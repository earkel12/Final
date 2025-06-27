package com.pm.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pm.reser.model.ReserDTO;
import com.pm.reser.model.UserCarDTO;
import com.pm.reser.service.ReserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class ReserController {

	@Autowired
	private ReserService service;
	
	@GetMapping("/reser")
	public String reserForm(HttpSession session, Model model) throws Exception {
	    String userId = (String) session.getAttribute("sid"); // 세션에서 사용자 ID 추출
	    List<UserCarDTO> carList = service.carbyid(userId);   // 차량 리스트 불러오기
	    model.addAttribute("carList", carList);               // View에 전달

	    return "reser/reser"; 
	}
	
	@PostMapping("/reser/payment")
	public String confirmReservation(@ModelAttribute ReserDTO booking,
	                                 HttpSession session, Model model,
	                                 @RequestParam("duration") int duration) throws Exception {
	    // 1. 세션에서 사용자 정보와 주차장 번호 가져오기
	    String userId = (String) session.getAttribute("sid");
	    Integer pIdx = (Integer) session.getAttribute("pidx");

	    booking.setId(userId);
	    booking.setIdx(pIdx);

	    // 2. 현재 시간 기준
	    LocalDateTime now = LocalDateTime.now();
	    booking.setBookingdate(now);
	    booking.setIntime(now);  // 입차 시간도 현재로 설정

	    // 3. 출차 시간 = 입차 + duration (단위: 시간)
	    booking.setOutime(now.plusHours(duration));

	    // 4. 기타 정보
	    booking.setStatus("예약접수");

	    // 5. 가격 계산
	    int base = 2000;
	    int per30 = 1000;
	    int timeCost = duration * 2 * per30;
	    booking.setPrice(base + timeCost);

	    // 6. insert 호출
	    service.insertReser(booking);

	    model.addAttribute("message", "예약이 완료되었습니다.");
	    return "reser/payment";
	}
	
	
}
