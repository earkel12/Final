package com.pm.controller;



import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;

import com.pm.notice.model.NoticeDTO;
import com.pm.notice.service.NoticeService;



import com.pm.page.PageModule;

import jakarta.servlet.http.HttpSession;


@Controller
public class NoticeController {

	@Autowired
	private NoticeService service;

	@GetMapping("/notice")
	public String noticeForm(Model model) {
		try {
			List<NoticeDTO> list = service.getAllNotice();
			model.addAttribute("noticeList",list);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/notice/list";
	}

	
	@GetMapping("/write")
	public String wirteForm() {
		return "/notice/write";
	}
	
	@PostMapping("/write")
	public ModelAndView write(NoticeDTO dto) {
		
		String msg = null;
		dto.setId("관리자");
		try {
			int result = service.noticeInsert(dto);
			msg = result > 0 ? "공지사항 등록 성공" : "공지사항 등록 실패";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.setViewName("notice/noticeMsg");
		return mav;
	}


	@GetMapping("/pm/notice")
	public ModelAndView showPmNotice(
			HttpSession session, 
			@RequestParam(value = "cp", defaultValue = "1") int cp) throws Exception {
		
		int listSize = 5;
		int pageSize = 5;
		int totalCnt = service.getPmTotalCnt();
	
		List<NoticeDTO> arr = service.getPmNotice(cp, listSize);
		String pageStr = PageModule.makePaging("/pm/notice", totalCnt, listSize, pageSize, cp);
		
		ModelAndView mav = new ModelAndView();
		String userid = (String) session.getAttribute("sid");
		mav.addObject("arr", arr);
		mav.addObject("userid", userid);
		mav.addObject("pageStr", pageStr);
		mav.setViewName("pm/notice");
		
		return mav;
	}

	@GetMapping("/pm/pmNoticeWrite")
	public String pmNoticeWrite() {
		return "/pm/pmNoticeWrite";
	}

	@PostMapping("/pm/pmNoticeWrite")
	public ModelAndView submitPmNotice(@ModelAttribute NoticeDTO dto) {
		dto.setId("admin");
		dto.setWritedate(new Date());
		dto.setReadnum(0);
		dto.setDivision(1);
		String msg = null;
		try {
			int result = service.insertPmNotice(dto);
			msg = result > 0 ? "공지사항 등록에 성공하셨습니다." : "공지사항 등록에 실패하셨습니다.";
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.addObject("gourl", "/pm/notice");
		mav.setViewName("pm/pmMsg");
		return mav;
	}


}
