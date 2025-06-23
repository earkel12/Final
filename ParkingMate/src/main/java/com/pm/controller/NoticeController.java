package com.pm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pm.notice.model.NoticeDTO;
import com.pm.notice.service.NoticeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService service;

	@GetMapping("/notice")
	public String noticeForm() {
		return "/notice/list";
	}

	@GetMapping("/pm/notice")
	public ModelAndView showPmNotice(HttpSession session) {
		int listSize = 5;
		int pageSize = 5;
		int totalCnt = 10;
		List<NoticeDTO> arr = new ArrayList<>();
		try {
			arr = service.getPmNotice();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("arr", arr);
		 String userid = (String) session.getAttribute("sid");
		mav.addObject("userid", userid);
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
			int result = service.insertNotice(dto);
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
