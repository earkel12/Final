package com.pm.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.pm.booking.model.BookingDTO;
import com.pm.booking.model.UserCarDTO;
import com.pm.booking.service.BookingService;
import com.pm.map.model.ParkingLotDTO;
import com.pm.pm.model.MatePayCheckDTO;

import jakarta.servlet.http.HttpSession;


@Controller
public class BookingController {

	@Autowired
	private BookingService service;
	
	@GetMapping("/search")
	public String searchForm() throws Exception {
		
		return "booking/search";
	}
	@GetMapping("/booking")
	public String bookingForm(HttpSession session, Model model) throws Exception {
	    String userId = (String) session.getAttribute("sid"); // 세션에서 사용자 ID 추출
	    List<UserCarDTO> carList = service.carbyid(userId);   // 차량 리스트 불러오기
	    model.addAttribute("carList", carList);               // View에 전달

	    return "booking/booking"; 
	}
	
	@PostMapping("/booking/agree")
	public String confirmReservation(@ModelAttribute BookingDTO booking,
	                                 HttpSession session, Model model,
	                                 @RequestParam("duration") int duration,
	                                 @RequestParam("total") int total) throws Exception {
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
	    booking.setOuttime(now.plusHours(duration));

	    // 4. 기타 정보
	    booking.setStatus("예약접수");

	    // 5. 가격 계산
	    booking.setPrice(total);
	    
	    // 6. insert 호출
	    service.insertBooking(booking);
	    
	    model.addAttribute("total", total);
	    
	    return "booking/agree";
	}
	
	@PostMapping("/payment")
	@ResponseBody
	public String updateBookingAfterPayment(HttpSession session) {
	    String userId = (String) session.getAttribute("sid");
	    try {
	        service.updateStatus(userId);  // 최신 예약 1건의 status를 예약접수로 변경
	        return "OK";
	    } catch (Exception e) {
	        return "FAIL";
	    }
	}
	
<<<<<<< HEAD
=======
	
	
	
	
	
	
	
	
	
	
	//메이트이용현황 관련
	@GetMapping("/mateUsagesStatus")
	public String mateUsagesStatus(@SessionAttribute("sid")String id, Model model) {
		
		System.out.println("사용자ID:"+ id);
		
		List<Map<String, Object>> mateBookingList = null;
		
		try {
			mateBookingList = service.showMatebookingList(id);
		} catch (Exception e) {
			System.out.println("메이트이용현황 오류발생! 고객센터에 연락바랍니다.");
			e.printStackTrace();
		}
		
		model.addAttribute("mateBookingList", mateBookingList);
		
		String parkinglotName = null;
		if(mateBookingList!=null && !mateBookingList.isEmpty()) {
			parkinglotName = (String)mateBookingList.get(0).get("parkinglot_name");
		}
		
		ParkingLotDTO parkinglotdto = null;
		if(parkinglotName!=null) {
			try {
				parkinglotdto = service.findParkinglotByName(parkinglotName);
			} catch (Exception e) {
				System.out.println("주차장 정보 조회 중 오류 발생");
	            e.printStackTrace();
			}
		}
		model.addAttribute("parkinglotdto", parkinglotdto);
		
		//지도위도, 경도추가
		if (mateBookingList != null && !mateBookingList.isEmpty()) {
		    Map<String, Object> booking = mateBookingList.get(0);

		    model.addAttribute("ulatitude", booking.get("ulatitude"));
		    model.addAttribute("ulongitude", booking.get("ulongitude"));
		    model.addAttribute("pmlatitude", booking.get("pmlatitude"));
		    model.addAttribute("pmlongitude", booking.get("pmlongitude"));
		}
		
		List<Map<String, Object>> findMate = null;
		
		if(mateBookingList!=null && !mateBookingList.isEmpty()) {
			String car_num = (String)mateBookingList.get(0).get("bookingcarnum");
		
			try {
				findMate = service.findMatcingMate(id, car_num);
			} catch (Exception e) {
				System.out.println("메이트 정보 조회 중 오류 발생");
	            e.printStackTrace();
			}
		}
		model.addAttribute("findMate", findMate);
		
		return "booking/mateUsagesStatus";
	}
	
	@PostMapping("/booking/updateOuttime")
	public String updateOuttime(@RequestParam("bookingnum") int bookingnum) {
	    try {
	        service.updateOuttime(bookingnum); // service → mapper → SQL update 수행
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return "redirect:/mateUsagesStatus";
	}
	
	
>>>>>>> e9064c3ae755ff524c0d05e67674937599450347
}
