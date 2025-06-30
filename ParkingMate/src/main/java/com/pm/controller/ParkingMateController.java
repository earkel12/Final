package com.pm.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class ParkingMateController {

	private final NoticeServiceImple noticeServiceImple;

	@Autowired
	private ParkingMateService service;

	ParkingMateController(NoticeServiceImple noticeServiceImple) {
		this.noticeServiceImple = noticeServiceImple;
	}

	@GetMapping("/pm/main")
	public ModelAndView pmMain(HttpSession session) throws Exception {
	    String userid = (String) session.getAttribute("sid");

	    if (userid == null || userid.isEmpty()) {
	        return new ModelAndView("redirect:/login");
	    }

	    ParkingMateDTO dto = service.getParkingMate(userid);
	    if (dto == null) {
	        return new ModelAndView("redirect:/pm/register");
	    }

	    ModelAndView mav = new ModelAndView("pm/main");
	    return mav;
	}

	@GetMapping("/parkingmate")
	public String redirectToPmMain() {
	    return "redirect:/pm/main";
	}
	@GetMapping("/pm/register")
	public ModelAndView GetPmRegister(HttpSession session) throws Exception {
	    String userid = (String) session.getAttribute("sid");

	    if (userid == null || userid.isEmpty()) {
	        return new ModelAndView("redirect:/login");
	    }

	    ParkingMateDTO dto = service.getParkingMate(userid);
	    if (dto != null) {
	        return new ModelAndView("redirect:/pm/main");
	    }

	    return new ModelAndView("pm/register");
	}

	@Value("${file.upload-dir}")
	private String uploadDir;

	@PostMapping("/pm/register")
	public ModelAndView PostPmRegister(HttpSession session, ParkingMateDTO dto,
			@RequestParam(value = "pictureFile", required = false) MultipartFile pictureFile) throws Exception {
		String userid = (String) session.getAttribute("sid");

		if (userid == null || userid.isEmpty()) {
			return new ModelAndView("pm/pmMsg", Map.of("msg", "로그인 후 이용 가능합니다.", "gourl", "/login"));
		}

		ParkingMateDTO existing = service.getParkingMate(userid);

		if (pictureFile != null && !pictureFile.isEmpty()) {
			String originalFilename = pictureFile.getOriginalFilename();
			String name = originalFilename;
			String extension = "";

			int dotIndex = originalFilename.lastIndexOf(".");
			if (dotIndex > 0) {
				name = originalFilename.substring(0, dotIndex);
				extension = originalFilename.substring(dotIndex);
			}

			File uploadPath = new File(uploadDir);
			if (!uploadPath.exists()) {
				uploadPath.mkdirs();
			}

			String savedFileName = originalFilename;
			File dest = new File(uploadDir, savedFileName);

			int count = 1;
			while (dest.exists()) {
				savedFileName = name + "(" + count + ")" + extension; // ex) myphoto(1).png
				dest = new File(uploadDir, savedFileName);
				count++;
			}

			pictureFile.transferTo(dest);
			dto.setPicture("/upload/" + savedFileName);
		}

		dto.setId(userid);

		int result;
		if (existing == null) {
			result = service.insertParkingMate(dto);
		} else {
			dto.setIdx(existing.getIdx());
			result = service.updateParkingMate(dto);
		}

		return new ModelAndView("pm/pmMsg", Map.of("msg", result > 0 ? "등록 성공" : "등록 실패", "gourl", "/pm/main"));
	}

	@GetMapping("/pm/usagehistory")
	public String showPmUsagehistory() {
		return "pm/usagehistory";
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
	public ModelAndView updateWorklog(HttpSession session, ParkingMateDTO dto,
			@RequestParam(value = "pictureFile", required = false) MultipartFile pictureFile) throws Exception {
		String userid = (String) session.getAttribute("sid");

		if (userid == null || userid.isEmpty()) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("msg", "로그인 후 이용 가능합니다.");
			mav.addObject("gourl", "/login");
			mav.setViewName("pm/pmMsg");
			return mav;
		}

		dto.setId(userid);

		if (pictureFile != null && !pictureFile.isEmpty()) {
			String originalFilename = pictureFile.getOriginalFilename();
			String name = originalFilename;
			String extension = "";

			int dotIndex = originalFilename.lastIndexOf(".");
			if (dotIndex > 0) {
				name = originalFilename.substring(0, dotIndex);
				extension = originalFilename.substring(dotIndex);
			}

			File uploadPath = new File(uploadDir); // uploadDir 변수는 서버의 저장 경로 (절대경로 권장)
			if (!uploadPath.exists()) {
				uploadPath.mkdirs();
			}

			String savedFileName = originalFilename;
			File dest = new File(uploadPath, savedFileName);

			int count = 1;
			while (dest.exists()) {
				savedFileName = name + "(" + count + ")" + extension;
				dest = new File(uploadPath, savedFileName);
				count++;
			}

			pictureFile.transferTo(dest);
			dto.setPicture(savedFileName);
		}

		int result = service.updateParkingMate(dto);

		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", result > 0 ? "근무 정보 수정 성공" : "근무 정보 수정 실패");
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

	@GetMapping("/pm/matching")
	public String showMatching() {
		return "/pm/matching";
	}

}
