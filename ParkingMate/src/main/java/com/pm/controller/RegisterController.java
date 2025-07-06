package com.pm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.pm.member.model.MemberDTO;
import com.pm.register.service.RegisterService;

@Controller
public class RegisterController {

	@Autowired
	private RegisterService service;

	@GetMapping("/register")
	public String register() {
		return "register/termsofuse";
	}

	@GetMapping("/choosetermsofuse")
	public String accepttermsofuse() {
		return "register/register";
	}

	
	@GetMapping("/userInfo")
	public String userInfo(@SessionAttribute("sid")String id, Model model) throws Exception {
		MemberDTO dto = service.selectUserById(id);
		model.addAttribute("dto", dto);
		
		return "register/userInfo";
	}
	
	@GetMapping("/registerUpdate")
	public String registerUpdate(@SessionAttribute("sid") String id, Model model) throws Exception {
	    MemberDTO dto = service.selectUserById(id); // 회원 정보 가져오기
	    model.addAttribute("dto", dto);

	    // 주민번호 쪼개서 따로 모델에 담기
	    if (dto.getResident_num() != null && dto.getResident_num().contains("-")) {
	        String[] rrn = dto.getResident_num().split("-");
	        model.addAttribute("resident1", rrn[0]);
	        model.addAttribute("resident2", rrn[1]);
	    }

	    return "register/registerUpdate";
	}
	
	@GetMapping("/checkId")
	@ResponseBody
	public String checkId(@RequestParam("id") String id) throws Exception {
	    boolean exists = service.checkId(id);
	    return exists ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.";
	}
	
	@GetMapping("/pwdUpdateFormPopup")
	public String pwdUpdateForm(@RequestParam String id, Model model) {
	    model.addAttribute("id", id);
	    return "register/pwdUpdateFormPopup";
	}
	
	@PostMapping("/registerForm")
	public ModelAndView registerForm(MemberDTO dto) {

		System.out.println(dto.toString());

		String msg = null;
		try {
			int result = service.registerForm(dto);
			msg = result>0?"회원가입 축하드립니다.":"회원가입에 실패하셨습니다.";
		} catch (Exception e) {
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.setViewName("register/registerMsg");
		return mav;
	}

 
	@PostMapping("/registerFormUpdate")
	public ModelAndView registerFormUpdate(MemberDTO dto,
											@SessionAttribute("sid")String id) {
		System.out.println(dto.toString());
		
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		map.put("id", id);
		
		String msg = null;
		
		try {
			int result = service.registerFormUpdate(map);
			msg = result>0?"회원정보수정성공":"회원정보수정실패";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "오류발생! 고객센터에 문의바랍니다.";
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.setViewName("register/registerMsg");
		return mav;
	}
	
	@PostMapping("/pwdUpdate")
	public ModelAndView pwdUpdate(@RequestParam String id, @RequestParam String currentPwd, @RequestParam String pwd, @RequestParam String confirmPwd) throws Exception {
		System.out.println("비밀번호변경요청ID:"+id);
		
		String msg = null;
		
		if (!pwd.equals(confirmPwd)) {
	        msg = "새 비밀번호와 확인 비밀번호가 일치하지 않습니다.";
	    } else {
		try {
			int result = service.pwdUpdate(id, currentPwd, pwd);
			 if (result == 1) {
	                msg = "비밀번호변경 성공";
	            } else if (result == -1) {
	                msg = "현재 비밀번호가 일치하지 않습니다.";
	            } else if (result == -2) {
	                msg = "동일한 비밀번호는 변경하실 수 없습니다.";
	            } else {
	                msg = "비밀번호 변경 실패 고객센터로 문의바랍니다.";
	            }
		} catch (Exception e) {
			e.printStackTrace();
			msg = "비밀번호 변경 중 오류가 발생했습니다.";
			}
	    }
		
		ModelAndView mav = new ModelAndView("register/registerMsg");
	    mav.addObject("msg", msg);
	    return mav;
	}
	
	
}
