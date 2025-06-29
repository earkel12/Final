package com.pm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pm.faq.model.FaqDTO;
import com.pm.faq.service.FaqService;

import jakarta.servlet.http.HttpSession;

@Controller
public class FaqController {

	@Autowired
	private FaqService service;

	@GetMapping("/faq")
	public String faqForm(HttpSession session, Model model) {
		List<FaqDTO> list = null;
		try {
			list = service.faqSelect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("list", list);
		String userid = (String)session.getAttribute("sid");
		model.addAttribute("userid", userid);
		return "/faq/list";
	}

	@GetMapping("/faqWrite")
	public String wirteForm() {
		return "/faq/write";
	}

	@PostMapping("/faqWrite")
	public ModelAndView faqWrite(FaqDTO dto) {
		String msg = null;

		try {
			int result = service.faqInsert(dto);
			msg = result > 0 ? "FAQ 등록 성공" : "FAQ 등록 실패";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.setViewName("faq/faqMsg");
		return mav;
	}

	@GetMapping("/faqEdit")
	public String faqEditForm(int idx, Model model) {
		try {
			FaqDTO dto = service.faqEditSel(idx);
			model.addAttribute("dto", dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "faq/faqEdit";
	}

	@PostMapping("/faqEdit")
	public ModelAndView faqEdit(FaqDTO dto) {
		ModelAndView mav = new ModelAndView();
		String msg = null;
		try {
			int result = service.faqUpdate(dto);
			msg = result > 0 ? "수정 성공!":"수정 실패!";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mav.addObject("msg",msg);
		mav.setViewName("/faq/faqMsg");

		return mav;
	}

	@GetMapping("/faqDelete")
	public ModelAndView faqDel(int idx) {
		String msg = null;

		try {
			int result = service.faqDelete(idx);
			msg = result > 0 ? "삭제 성공":"삭제 실패";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg",msg);
		mav.setViewName("/faq/faqMsg");
		return mav;
	}
}
