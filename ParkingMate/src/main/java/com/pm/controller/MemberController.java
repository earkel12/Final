package com.pm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pm.member.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired
	private MemberService service;


	@GetMapping("/login")
	public String loginForm() {
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
			mav.addObject("msg", dbname+"님 환영합니다.");
			mav.addObject("gourl", "/");
			mav.setViewName("member/login_ok");

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
			mav.addObject("msg", "아이디 또는 비밀번호가 잘못되었습니다.");
			mav.addObject("gourl", "/login");
			mav.setViewName("member/memberMsg");
		}else if(result == MemberService.ERROR) {
			mav.addObject("msg", "예기치 못한 에러가 발생했습니다. 로그인을 다시 시도해주세요.");
			mav.addObject("gourl", "/login");
			mav.setViewName("member/memberMsg");
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
}
