package com.pm.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pm.booking.model.BookingDTO;
import com.pm.booking.model.UserCarDTO;
import com.pm.booking.service.BookingService;
import com.pm.map.model.ParkingLotDTO;
import com.pm.mapper.BookingMapper;
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
	
	@GetMapping("/location")
	public String locationPopup() throws Exception {
		
		return "booking/location";
	}

	@GetMapping("/booking")
	public String bookingForm(HttpSession session, Model model) throws Exception {
	    String userid = (String) session.getAttribute("sid"); // 세션에서 사용자 ID 추출
	    String pname = (String) session.getAttribute("pname");
	    		
	    if (userid == null || userid.isEmpty()) {
	        return "redirect:/login?alert=2";  // 리다이렉트
	    } else if(pname==null || pname.isEmpty()) {
        	return "redirect:/?alert=1";
        }
	    
	    List<UserCarDTO> carList = service.carbyid(userid);   // 차량 리스트 불러오기
	    
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
	public String updatePayment(HttpSession session) {
	    String userId = (String) session.getAttribute("sid");
	    try {
	        service.updateStatus(userId);
	        return "OK";
	    } catch (Exception e) {
	        return "FAIL";
	    }
	}
	
	@PostMapping("/finalpayment")
	@ResponseBody
	public String finalupdatePayment(HttpSession session, @RequestParam("bookingcarnum") String bookingcarnum) {
	    String userId = (String) session.getAttribute("sid");
	    System.out.println("userId: " + userId);
	    System.out.println("bookingcarnum: " + bookingcarnum);

	    try {
	        if (bookingcarnum == null || bookingcarnum.trim().isEmpty()) {
	            System.out.println("bookingcarnum 파라미터가 전달되지 않았습니다.");
	            return "FAIL";
	        }

	        int price = service.getBookingPriceById(userId, bookingcarnum);
	        System.out.println("price: " + price);

	        if (price == 0) {
	            service.updateBookingStatusIfPriceZero(userId, bookingcarnum);
	            System.out.println("0원 결제 상태 업데이트 완료");
	            return "OK";
	        } else {
	            service.finalupdateStatus(userId, bookingcarnum);
	            System.out.println("유료 결제 상태 업데이트 완료");
	            return "OK";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "FAIL";
	    }
	}

	//메이트이용현황 관련
	@GetMapping("/mateUsagesStatus")
	public String mateUsagesStatus(@SessionAttribute(value = "sid", required = false) String id,
	        @RequestParam(value = "bookingcarnum", required = false) String bookingcarnum, Model model, RedirectAttributes rttr ) {
		System.out.println("사용자ID:"+ id);
		
		if (id == null || id.isEmpty()) {
		   System.out.println("세션 만료 또는 미로그인 상태, 로그인 페이지로 리다이렉트");
		   return "redirect:/login?alert=2";
		}
		
		try {
	        List<String> reservedCarNums = service.findBookingCarNumByUser(id);
	        model.addAttribute("reservedCarNums", reservedCarNums);
	        
	        if (reservedCarNums == null || reservedCarNums.isEmpty()) {
	            rttr.addFlashAttribute("msg", "현재 이용 중인 예약건이 없습니다.");
	            return "redirect:/alertForNotExistMateUsage";
	        }
	        
	        if (bookingcarnum == null && !reservedCarNums.isEmpty()) {
	        	bookingcarnum = reservedCarNums.get(0);
	        }
	        model.addAttribute("selectedCarNum", bookingcarnum);

	    	} catch (Exception e) {
	        e.printStackTrace();
	    	}
		
		List<Map<String, Object>> bookingInfoByCarnum = null;
		
		try {
			bookingInfoByCarnum = service.findBookingInfoByCarNum(id, bookingcarnum);
			
			// intime, outtime 업데이트
			for (Map<String, Object> booking : bookingInfoByCarnum) {
			    Timestamp ts = (Timestamp) booking.get("display_intime");
			    if (ts != null) {
			        LocalDateTime ldt = ts.toLocalDateTime();
			        booking.put("display_intime", ldt);
			        booking.put("intime", ldt);  // thymeleaf 조건 작동
			    }

			    Timestamp ots = (Timestamp) booking.get("outtime");
			    if (ots != null) {
			        LocalDateTime oldt = ots.toLocalDateTime();
			        booking.put("outtime", oldt);  // outtime도 LocalDateTime으로 변환
			    }
			}
			
		} catch (Exception e) {
			System.out.println("메이트이용현황 오류발생! 고객센터에 연락바랍니다.");
			e.printStackTrace();
		}
		
		if (bookingInfoByCarnum != null && !bookingInfoByCarnum.isEmpty()) {
		    Map<String, Object> booking = bookingInfoByCarnum.get(0);
		    model.addAttribute("booking", booking);
		    model.addAttribute("intime", booking.get("intime"));
		    model.addAttribute("outtime", booking.get("outtime"));
		    model.addAttribute("price", booking.get("price"));
		    model.addAttribute("instand", booking.get("instand")); 
		}
		
		model.addAttribute("bookingInfoByCarnum", bookingInfoByCarnum);
		
		String parkinglotName = null;
		if(bookingInfoByCarnum!=null && !bookingInfoByCarnum.isEmpty()) {
			parkinglotName = (String)bookingInfoByCarnum.get(0).get("parkinglot_name");
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
		
		if (parkinglotdto == null || bookingInfoByCarnum == null) {
	        model.addAttribute("noBooking", true);
	        return "booking/mateUsagesStatus";
	    }
		
		model.addAttribute("parkinglotdto", parkinglotdto);
		
		//지도위도, 경도추가
		if (bookingInfoByCarnum != null && !bookingInfoByCarnum.isEmpty()) {
		    Map<String, Object> booking = bookingInfoByCarnum.get(0);

		    model.addAttribute("ulatitude", booking.get("ulatitude"));
		    model.addAttribute("ulongitude", booking.get("ulongitude"));
		    model.addAttribute("pmlatitude", booking.get("pmlatitude"));
		    model.addAttribute("pmlongitude", booking.get("pmlongitude"));
		}
		
		List<Map<String, Object>> findMate = null;
		
		if(bookingInfoByCarnum!=null && !bookingInfoByCarnum.isEmpty()) {
			String car_num = (String)bookingInfoByCarnum.get(0).get("bookingcarnum");
		
			System.out.println("조회된 car_num: " + car_num);
			
			try {
				findMate = service.findMatcingMate(id, car_num);
			} catch (Exception e) {
				System.out.println("메이트 정보 조회 중 오류 발생");
	            e.printStackTrace();
			}
		}
		model.addAttribute("findMate", findMate);
		System.out.println("들어감");
		return "booking/mateUsagesStatus";
	}
	
	@GetMapping("/alertForNotExistMateUsage")
    public String alertForNotExistMateUsage() {
		return "booking/alertForNotExistMateUsage";
    }
	
	
	@PostMapping("/booking/updateIntime")
	@ResponseBody
	public Map<String, Object> updateIntime(@RequestParam("bookingnum")int bookingnum, HttpSession session) {
		System.out.println("bookingnum: " + bookingnum);
		
		Map<String, Object> result = new HashMap<>();
		
		try {
			String id = (String) session.getAttribute("sid");
			if(id==null) {
				result.put("success", false);
	            result.put("error", "세션 만료. 로그인 필요");
	            result.put("redirect", "/login"); // redirect URL 전달
	            return result;
			}
	        service.updateIntime(bookingnum);
	        result.put("success", true);

	    } catch (Exception e) {
	    	e.printStackTrace();
	        result.put("success", false);
	        result.put("error", e.getMessage());
	    }
		 return result;
	}
	
	@PostMapping("/booking/updateOuttime")
	@ResponseBody
	public Map<String, Object> updateOuttime(@RequestParam("bookingnum") int bookingnum, HttpSession session) {
	    System.out.println("bookingnum: " + bookingnum);

	    Map<String, Object> result2 = new HashMap<>();
	    try {
	        String id = (String) session.getAttribute("sid");
	        if(id == null) {
	            result2.put("success", false);
	            result2.put("error", "세션 만료. 로그인 필요");
	            result2.put("redirect", "/login");
	            return result2;
	        }

	        Map<String, Object> data = service.updateOuttime(bookingnum);

	        result2.put("success", true);
	        result2.put("price", data.get("price"));
	        result2.put("minutes", data.get("minutes"));

	        // 출차시간 응답에 포함
	        Timestamp outtimeTimestamp = (Timestamp) data.get("outtime");
	        if (outtimeTimestamp != null) {
	            LocalDateTime outtimeLdt = outtimeTimestamp.toLocalDateTime();
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	            result2.put("outtime", outtimeLdt.format(formatter));
	        } else {
	            result2.put("outtime", "-");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        result2.put("success", false);
	        result2.put("error", e.getMessage());
	    }
	    return result2;
	}

}
