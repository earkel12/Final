package com.pm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pm.ask.model.AskDTO;
import com.pm.ask.service.AskService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AskContoller {
	
	@Autowired
	private AskService service;
	
	@GetMapping("/ask")
	public ModelAndView asklist(HttpSession session) {
		String userid = "";
		userid = (String)session.getAttribute("sid");
		ModelAndView mav = new ModelAndView();
		mav.addObject("userid", userid);
		mav.setViewName("ask/asklist");
		return mav;
	}

	@GetMapping("/askWrite")
	public String askWriteForm(Model model, HttpSession session) {
		
		String userid = "";
		userid = (String)session.getAttribute("sid");
		model.addAttribute("userid",userid);
		return "ask/askWrite";
	}
	
	@PostMapping("/askWrite")
	public ModelAndView askWrite(AskDTO dto) {
		String msg = null;
		try {
			int result = service.askInsert(dto);
			msg = result > 0 ? "문의 성공" : "문의 실패";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg",msg);
		mav.setViewName("ask/askMsg");
		return mav;
		
	}
	
}
