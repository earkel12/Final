package com.pm.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pm.booking.model.BookingDTO;
import com.pm.booking.model.BookingParkingDTO;
import com.pm.booking.model.BookingDTO;
import com.pm.booking.service.BookingService;
import com.pm.notice.service.NoticeServiceImple;
import com.pm.pm.model.MatePayCheckDTO;
import com.pm.pm.model.ParkingMateDTO;
import com.pm.pm.service.ParkingMateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
public class ParkingMateController {

	private final NoticeServiceImple noticeServiceImple;

	@Autowired
	private BookingService bookingservice;

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
			dto.setPicture(savedFileName);
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
	public ModelAndView showPmUsagehistory(HttpSession session) throws Exception {
		String mateId = (String) session.getAttribute("sid");

		if (mateId == null || mateId.isEmpty()) {
			return new ModelAndView("redirect:/login");
		}

		List<BookingParkingDTO> usageList = bookingservice.getBookingParkingListByMateId(mateId);

		ModelAndView mav = new ModelAndView("pm/usagehistory");
		mav.addObject("usageList", usageList);
		return mav;
	}

	@GetMapping("/pm/worklog")
	public ModelAndView showWorklog(HttpSession session) throws Exception {
		String userid = (String) session.getAttribute("sid");
		ModelAndView mav = new ModelAndView();
		if (userid == null || userid.isEmpty()) {
			mav.addObject("msg", "로그인 후 이용 가능합니다.");
			mav.addObject("gourl", "/login");
			mav.setViewName("pm/pmMsg");
			return mav;
		}

		ParkingMateDTO parkingMate = service.getParkingMate(userid);
		if (parkingMate == null) {
			mav.addObject("msg", "파킹메이트 등록 후 이용 가능합니다.");
			mav.addObject("gourl", "/parkingmate");
			mav.setViewName("pm/pmMsg");
			return mav;
		}
		String addr = parkingMate.getAddr();

		if (addr != null && !addr.isBlank()) {
			 String[] parts = addr.split(" ", 5); 
			    if (parts.length >= 4) {
			        String mainAddr = String.join(" ", parts[0], parts[1], parts[2], parts[3]); // "서울특별시 강남구 테헤란로"
			        String detailAddr = addr.replace(mainAddr, "").trim(); 

			        mav.addObject("mainAddr", mainAddr); 
			        mav.addObject("addrDetail", detailAddr);
			    } else {
			    	mav.addObject("mainAddr", addr);
			    	mav.addObject("addrDetail", "");
			    }
		}
		
		Map<String, Object> summary = service.getTotalPmWorklog(userid);
		
		mav.addObject("worklog", parkingMate);
		mav.addObject("parkingMate", parkingMate);
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

			File uploadPath = new File(uploadDir);
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
		} else {
			ParkingMateDTO existing = service.getParkingMate(dto.getId());
			if (existing != null && existing.getPicture() != null) {
				dto.setPicture(existing.getPicture());
			} else {
				throw new IllegalArgumentException("운전면허증 이미지는 반드시 등록해야 합니다.");
			}
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
	public ModelAndView showMatching(HttpSession session) throws Exception {
	    ModelAndView mav = new ModelAndView();
	    String sid = (String) session.getAttribute("sid");
	    
	    List<BookingParkingDTO> list = bookingservice.getActiveInstadBookings();
	    
	    Map<String, List<Integer>> rejectedMap = (Map<String, List<Integer>>) session.getAttribute("rejectedBookingsMap");

	    List<Integer> rejected = (rejectedMap != null && rejectedMap.containsKey(sid))
	            ? rejectedMap.get(sid)
	            : Collections.emptyList(); 

	    list.removeIf(item -> rejected.contains(Integer.parseInt(item.getBookingnum())));
	    
	    mav.addObject("instadList", list);
	    mav.setViewName("pm/matching");
	    return mav;
	}

	@PostMapping("/pm/matching/accept")
	public ModelAndView accept(@RequestParam int bookingnum, HttpSession session) {
		String mateId = (String) session.getAttribute("sid");
		ModelAndView mav = new ModelAndView("pm/pmMsg");
		
		int waitingCount = service.getSettlementWaitingCount(mateId);
	    if (waitingCount >= 1) {
	        mav.addObject("msg", "정산대기 상태의 예약이 2건 이상 있어 수락할 수 없습니다. 먼저 정산을 완료해주세요.");
	        mav.addObject("gourl", "/pm/matching");
	        return mav;
	    }
		
		bookingservice.updateStatusToReserved(bookingnum);
		BookingDTO booking = bookingservice.getBookingByNum(bookingnum);

		LocalDateTime intime = booking.getIntime();
		LocalDateTime outtime = booking.getOuttime();
		Date starttime = Date.from(intime.atZone(ZoneId.systemDefault()).toInstant());
		Date endtime = Date.from(outtime.atZone(ZoneId.systemDefault()).toInstant());

		MatePayCheckDTO dto = new MatePayCheckDTO();
		dto.setId(booking.getId());
		dto.setMid(mateId);
		dto.setCar_num(booking.getBookingcarnum());
		dto.setStarttime(starttime);
		dto.setEndtime(endtime);
		dto.setStatus("정산대기");
		dto.setPrice(booking.getPrice());
		
		service.insertMatePayCheck(dto);
		
		mav.setViewName("redirect:/pm/matching");
	    return mav;
	}

	@PostMapping("/pm/matching/reject")
	public String reject(@RequestParam int bookingnum, HttpSession session) {
	    String sid = (String) session.getAttribute("sid");  // 로그인된 사용자 ID

	    Map<String, List<Integer>> rejectedMap =
	        (Map<String, List<Integer>>) session.getAttribute("rejectedBookingsMap");

	    if (rejectedMap == null) {
	        rejectedMap = new HashMap<>();
	    }
	    
	    List<Integer> rejectedList = rejectedMap.getOrDefault(sid, new ArrayList<>());

	    if (!rejectedList.contains(bookingnum)) {
	        rejectedList.add(bookingnum);
	    }

	    rejectedMap.put(sid, rejectedList);  // 다시 저장
	    session.setAttribute("rejectedBookingsMap", rejectedMap);

	    return "redirect:/pm/matching";
	}
	
	@GetMapping("mylocation")
	public String mylocation() {
	    return "/pm/mylocation";
	}
	
	@GetMapping("parking")
	public ModelAndView showParking(HttpSession session) throws Exception {
		String mateId = (String) session.getAttribute("sid");

		if (mateId == null || mateId.isEmpty()) {
			return new ModelAndView("redirect:/login");
		}

		List<BookingParkingDTO> usageList = bookingservice.getBookingParkingListByMateId(mateId);

		ModelAndView mav = new ModelAndView("pm/parking");
		mav.addObject("usageList", usageList);
		return mav;
	}
}
