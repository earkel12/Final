package com.pm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pm.notice.service.NoticeService;
import com.pm.notice.service.NoticeServiceImple;
import com.pm.pm.model.ParkingMateDTO;
import com.pm.pm.service.ParkingMateService;


@Controller
public class ParkingMateController {

    private final NoticeServiceImple noticeServiceImple;
	
	@Autowired
	private ParkingMateService service;

    ParkingMateController(NoticeServiceImple noticeServiceImple) {
        this.noticeServiceImple = noticeServiceImple;
    }

	@GetMapping("/pm/main")
	public String GetPmMain() {
		return "pm/main";
	}

	@GetMapping("/pm/register")
	public String GetPmRegister() {
		
		return "pm/register";
	}

	@PostMapping("/pm/register")
	public ModelAndView PostPmRegister(ParkingMateDTO dto) {
	    String msg = null;
	    try {
			int result = service.insertParkingMate(dto);
			msg = result>0?"파킹메이트 등록에 성공하셨습니다.":"파킹메이트 등록에 실패하셨습니다.";
		} catch (Exception e) {
			e.printStackTrace();
		}
	    ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.addObject("gourl", "/pm/main");
		mav.setViewName("pm/pmMsg");
	    return mav;
	}

	@GetMapping("/pm/settlement")
	public String showPmSettlement() {

		return "pm/settlement";
	}

	@GetMapping("/pm/usagehistory")
	public String showPmUsagehistory() {

		return "pm/usagehistory";
	}

	@GetMapping("/pm/worklog")
	public String showWorklog() {

		return "pm/worklog";
	}

}
