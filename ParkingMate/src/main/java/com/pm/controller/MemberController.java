package com.pm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pm.booking.model.BookingDTO;
import com.pm.member.model.MemberDTO;
import com.pm.member.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired
	private MemberService service;


	@GetMapping("/login")
	public String loginForm(HttpServletRequest request, Model model) {
		String saveid = null;
		Cookie[] ck = request.getCookies();
		if(ck != null)	 {
			for(Cookie c : ck) {
				if(c.getName().equals("saveid")) {
					saveid = c.getValue();
					break;
				}
			}
		}
		model.addAttribute("saveid", saveid);
		return "member/login";
	}

	@PostMapping("/login")
	public ModelAndView login(String userid, String userpwd, HttpSession session,
			@RequestParam(value = "saveid", defaultValue = "off")String saveid,
			HttpServletResponse resp) {
		int result = 0;
		try {
			result = service.loginCheck(userid, userpwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView();

		try {
			if(result == MemberService.LOGIN_OK) {
			String dbname = service.userInfo(userid);
			session.setAttribute("sid", userid);
			session.setAttribute("sname", dbname);
			//mav.addObject("msg", dbname+"님 환영합니다.");
			mav.addObject("gourl", "/");
			mav.setViewName("/index");

			if(saveid.equals("on")) {
				Cookie ck = new Cookie("saveid", userid);
				ck.setMaxAge(60*60*24*30);
				resp.addCookie(ck);
			}else {
				Cookie saveidCookie = new Cookie("saveid", "");
				saveidCookie.setMaxAge(0);
				resp.addCookie(saveidCookie);
			}
		}else if(result == MemberService.NOT_ID || result == MemberService.NOT_PWD) {
			//mav.addObject("msg", "아이디 또는 비밀번호가 잘못되었습니다.");
			mav.addObject("gourl", "/login");
			mav.addObject("error", "아이디 또는 비밀번호가 잘못되었습니다.");
			mav.addObject("userid",userid);
			mav.setViewName("member/login");
		}else if(result == MemberService.ERROR) {
			mav.addObject("error", "예기치 못한 에러가 발생했습니다. 로그인을 다시 시도해주세요.");
			mav.addObject("gourl", "/login");
			mav.setViewName("member/login");
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mav;
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/idFind")
	public String idFindForm() {
		return "member/idFind";
	}

	@GetMapping("/pwdFind")
	public String pwdFindForm() {
		return "member/pwdFind";
	}
	
	@PostMapping("/idFind")
	public ModelAndView idFind(MemberDTO dto) {
		String userid = null;
		try {
			userid = service.idFind(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("userid", userid);
		mav.addObject("useridSel", true);
		mav.setViewName("member/idFind");
		return mav;
	}
	
	@PostMapping("/pwdFind")
	public ModelAndView pwdFind(MemberDTO dto) {
		String pwd = null;
		
		try {
			pwd = service.pwdFind(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("userpwd", pwd);
		mav.addObject("userpwdSel", true);
		mav.setViewName("member/pwdFind");
		return mav;
	}
	
	
	
	
	
	//관리자모드-회원리스트
	@GetMapping("/memberListAdminMode")
	public String memberListAdminMode(HttpSession session, Model model) throws Exception {
		String id = (String) session.getAttribute("sid");
		if(id == null || !id.equals("admin")) {
			return "redirect:/";
		}
		
		List<Map<String, Object>> memberList = service.checkMemberListByAdmin();
		model.addAttribute("memberList", memberList);
		return "member/memberListAdminMode";
	}
	//관리자모드-특정회원예약내역확인
	@GetMapping("/memberBookingListAdminMode")
	public String memberBookingListAdminMode(@RequestParam("id") String memberid, HttpSession session, Model model) throws Exception {
	    String id = (String) session.getAttribute("sid");
	    if (id == null || !id.equals("admin")) {
	        return "redirect:/";
	    }
	    List<Map<String, Object>> bookingList = service.findBookingListById(memberid);
	    model.addAttribute("bookingList", bookingList);
	    return "member/memberBookingListAdminMode";
	}
	
	
}
