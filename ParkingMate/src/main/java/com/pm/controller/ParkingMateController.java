package com.pm.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.pm.notice.service.NoticeServiceImple;
import com.pm.pm.model.MatePayCheckDTO;
import com.pm.pm.model.ParkingMateDTO;
import com.pm.pm.service.ParkingMateService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ParkingMateController {

	private final NoticeServiceImple noticeServiceImple;

	@Autowired
	private ParkingMateService service;

	ParkingMateController(NoticeServiceImple noticeServiceImple) {
		this.noticeServiceImple = noticeServiceImple;
	}

	@GetMapping("/parkingmate")
	public String GetPmMain() {
		return "/pm/main";
	}

	@GetMapping("/pm/register")
	public ModelAndView GetPmRegister(HttpSession session) {
		String userid = (String) session.getAttribute("sid");

		if (userid == null || userid.isEmpty()) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("msg", "로그인 후 이용 가능합니다.");
			mav.addObject("gourl", "/login");
			mav.setViewName("pm/pmMsg");
			return mav;
		}
		
		ModelAndView mav = new ModelAndView();
	    mav.setViewName("pm/register");
	    return mav;

	}

	@PostMapping("/pm/register")
	public ModelAndView PostPmRegister(HttpSession session, ParkingMateDTO dto) throws Exception {
		String userid = (String) session.getAttribute("sid");

		if (userid == null || userid.isEmpty()) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("msg", "로그인 후 이용 가능합니다.");
			mav.addObject("gourl", "/login");
			mav.setViewName("pm/pmMsg");
			return mav;
		}

		dto.setId(userid);
		int result = service.insertParkingMate(dto);
		String msg = result > 0 ? "파킹메이트 등록에 성공하셨습니다." : "파킹메이트 등록에 실패하셨습니다.";

		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.addObject("gourl", "/pm/main");
		mav.setViewName("pm/pmMsg");
		return mav;
	}

	@GetMapping("/pm/worklog")
	public ModelAndView showWorklog(HttpSession session) throws Exception {
		String userid = (String) session.getAttribute("sid");

		if (userid == null || userid.isEmpty()) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("msg", "로그인 후 이용 가능합니다.");
			mav.addObject("gourl", "/login");
			mav.setViewName("pm/pmMsg");
			return mav;
		}

		ParkingMateDTO dto = service.getParkingMate(userid);

		if (dto == null) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("msg", "파킹메이트 등록 후 이용 가능합니다.");
			mav.addObject("gourl", "/parkingmate");
			mav.setViewName("pm/pmMsg");
			return mav;
		}
		Map<String, Object> summary = service.getTotalPmWorklog(userid);
		ModelAndView mav = new ModelAndView();
		mav.addObject("worklog", dto);
		mav.addObject("totalServiceCount", summary.get("totalServiceCount"));
		mav.addObject("totalPayCount", summary.get("totalPayCount"));
		mav.setViewName("pm/worklog");
		return mav;
	}

	@PostMapping("/pm/worklog")
	public ModelAndView updateWorklog(HttpSession session, ParkingMateDTO dto) throws Exception {
		String userid = (String) session.getAttribute("sid");

		if (userid == null || userid.isEmpty()) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("msg", "로그인 후 이용 가능합니다.");
			mav.addObject("gourl", "/login");
			mav.setViewName("pm/pmMsg");
			return mav;
		}

		dto.setId(userid);
		int result = service.updateParkingMate(dto);
		String msg = result > 0 ? "근무 정보 수정 성공" : "근무 정보 수정 실패";
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.addObject("gourl", "/pm/worklog");
		mav.setViewName("pm/pmMsg");
		return mav;
	}

	@GetMapping("/pm/settlement")
	public ModelAndView showPmSettlement(HttpSession session, @RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate) throws Exception {

		String userid = (String) session.getAttribute("sid");
		if (userid == null) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("msg", "로그인 후 이용 가능합니다.");
			mav.addObject("gourl", "/login");
			mav.setViewName("pm/pmMsg");
			return mav;
		}

		List<MatePayCheckDTO> arr = service.getMatePayCheck(userid, startDate, endDate);

		ModelAndView mav = new ModelAndView();
		mav.addObject("arr", arr);
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);
		mav.setViewName("pm/settlement");
		return mav;
	}

	@GetMapping("/pm/usagehistory")
	public String showPmUsagehistory() {
		return "pm/usagehistory";
	}

}
