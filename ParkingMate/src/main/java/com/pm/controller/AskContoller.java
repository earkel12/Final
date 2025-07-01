package com.pm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pm.ask.model.AskDTO;
import com.pm.ask.service.AskService;
import com.pm.page.PageModule;

import jakarta.servlet.http.HttpSession;

@Controller
public class AskContoller {
	
	@Autowired
	private AskService service;
	
	@GetMapping("/ask")
	public ModelAndView asklist(HttpSession session,
			@RequestParam(value = "cp", defaultValue = "1") int cp) throws Exception{

		int listSize = 5;
		int pageSize = 5;
		int totalCnt = service.getTotalCnt();
		
		List<AskDTO> askList = service.getAskList(cp, listSize);
		String pageStr = PageModule.makePaging("/ask", totalCnt, listSize, pageSize, cp);
		
		String userid = (String)session.getAttribute("sid");
		ModelAndView mav = new ModelAndView();
		mav.addObject("userid", userid);
		mav.addObject("askList", askList);
		mav.addObject("pageStr", pageStr);
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
		String goUrl = "/";
		try {
			int result = service.askInsert(dto);
			msg = result > 0 ? "문의 성공" : "문의 실패";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg",msg);
		mav.addObject("goUrl", goUrl);
		mav.setViewName("ask/askMsg");
		return mav;
		
	}
	
	@GetMapping("/askComment")
	public ModelAndView askComment(int idx, HttpSession session) {
		List<AskDTO> askComment = null;
		String userid = null;
		userid = (String)session.getAttribute("sid");
		try {
			askComment = service.getAskComment(idx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("userid", userid);
		mav.addObject("askComment", askComment);
		mav.setViewName("ask/askComment");
		return mav;
	}
	
	@PostMapping("/comment")
	public ModelAndView askUpdate(AskDTO dto) {
		String msg = null;
		String goUrl = "ask";
		try {
			int result = service.askUpdate(dto);
			msg = result > 0 ? "답변 등록성공" : "답변 등록 실패";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.addObject("goUrl", goUrl);
		mav.setViewName("ask/askMsg");
		return mav;
	}
	
	@GetMapping("/result")
	public ModelAndView accomplishedAskList(HttpSession session,
			@RequestParam(value = "cp", defaultValue = "1")int cp) throws Exception{
		int listSize = 5;
		int pageSize = 5;
		int totalCnt = service.getTotalCnt2();
		
		List<AskDTO> askList = service.accomplishedAskList(cp, listSize);
		
		String pageStr = PageModule.makePaging("/result", totalCnt, listSize, pageSize, cp);
		
		String userid = (String)session.getAttribute("sid");
		ModelAndView mav = new ModelAndView();
		mav.addObject("userid", userid);
		mav.addObject("askList", askList);
		mav.addObject("pageStr", pageStr);
		mav.setViewName("ask/result");
		return mav;
		
	}

	@GetMapping("/resultContent")
	public ModelAndView resultContent(int idx, HttpSession session) {
		
		
		List<AskDTO> askContent = null;
		String userid = null;
		userid = (String)session.getAttribute("sid");
		try {
			askContent = service.getAskComment(idx);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("userid", userid);
		mav.addObject("askContent", askContent);
		mav.setViewName("ask/resultContent");
		return mav;
	}
}
