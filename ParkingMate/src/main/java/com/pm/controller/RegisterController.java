package com.pm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@GetMapping("/checkId")
	@ResponseBody
	public String checkId(@RequestParam("id") String id) throws Exception {
	    boolean exists = service.checkId(id);
	    return exists ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디입니다.";
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



}
