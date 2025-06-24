package com.pm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pm.notice.model.NoticeDTO;
import com.pm.notice.service.NoticeService;



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
}
