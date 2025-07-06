package com.pm.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import com.pm.booking.model.BookingDTO;
import com.pm.mypage.model.Car_TypeDTO;
import com.pm.mypage.model.User_CarsDTO;
import com.pm.mypage.service.MypageService;

@Controller
public class MypageController {

	@Autowired
	private MypageService service;
	
	@GetMapping("/mypageMain")
	public String MypageMain() {
		return "mypage/mypageMain";
	}
	
	@GetMapping("/myCarAdd")
	public String Mypageintousercars() {
		return "mypage/myCarAdd";
	}
	
	@GetMapping("/toFindCarPopup")
	public String toFindCarPopup() {
		return "mypage/toFindCarPopup";
	}
	
	@GetMapping("/myParkingHistory")
	public String checkMyParkingHistoryList(@SessionAttribute("sid")String id, Model model) {
		
		System.out.println("이용자ID:"+id);
		
		List<Map<String, Object>> bookingList = null;
		//리뷰
		List<Integer> reviewList= null;
		try {
			bookingList=service.checkMyParkingHistoryList(id);
			//리뷰
			reviewList=service.checkReview(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("bookingList", bookingList);
		//리뷰
		model.addAttribute("reviewList", reviewList);
		return "mypage/myParkingHistory";
	}
	
	@GetMapping("/myParkingMateHistory")
	public String checkMyParkingMateHistoryList(@SessionAttribute("sid")String id, @RequestParam(value = "status", required = false) String status, Model model) {
		
		
		System.out.println("이용자ID:"+id);
		System.out.println("가져온 상태값:" + status);
		
		List<Map<String, Object>> bookingParkingMateList = null;
		//리뷰
		List<Integer> reviewList= null;
		try {
			bookingParkingMateList=service.checkMyParkingMateHistoryList(id);
			//리뷰
			reviewList=service.checkReview(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("bookingParkingMateList", bookingParkingMateList);
		//리뷰
		model.addAttribute("reviewList", reviewList);
		return "mypage/myParkingMateHistory";
	}
	
	@GetMapping("/myCarInfoShow")
	public String showMyCarInfo(@RequestParam("car_num")String car_num, Model model) throws Exception {
		
		System.out.println("수정요청차량번호:"+car_num);
		
		User_CarsDTO dto = service.showMyCarInfo(car_num);
		model.addAttribute("dto", dto);
		
		return "mypage/myCarInfoUpdate";
	}
	
	//getmapping으로 삭제메소드 만들 예정
	@GetMapping("/myCarDelete")
	public ModelAndView myCarDelete(@RequestParam("car_num")String car_num) {
		
		System.out.println("삭제요청차량번호:"+car_num);
		
		String msg = null;
		try {
			int count = service.myCarDelete(car_num);
			msg = count>0?"차량삭제성공":"차량삭제실패";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "오류발생! 고객센터에 문의 바랍니다.";
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.setViewName("mypage/mypageMsg");
		return mav;
	}
	
	@GetMapping("/myCarList")
	public String checkMyCarList(@SessionAttribute("sid")String id,
								 Model model) {
		
		System.out.println("사용자ID:"+id);
		
		List<User_CarsDTO> carList = null;
		
		try {
			carList=service.checkMyCarList(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("carList", carList);
		return "mypage/myCarList";
	}
	
	@PostMapping("/carRegisterForm")
	public ModelAndView carRegisterForm(User_CarsDTO dto) {
		
		System.out.println(dto.toString());
		
		String msg = null;
		
		try {
			int result = service.carRegisterForm(dto);
			msg = result>0?"차량등록완료":"차량등록실패";
		} catch (IllegalAccessException e) {
			msg = e.getMessage();
		} catch(Exception e) {
			msg = "오류발생! 고객센터에 문의바랍니다.";
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.setViewName("mypage/mypageMsg");
		return mav;
	}
	
	@PostMapping("/updateMyCarInfo")
	public ModelAndView updateMyCarInfo(User_CarsDTO dto, @RequestParam String originalCarNum) {
		
		System.out.println(dto.toString());
		System.out.println("▶▶ originalCarNum: " + originalCarNum);
		System.out.println("▶▶ dto = " + dto);
		
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		map.put("originalCarNum", originalCarNum);
		
		String msg = null;
		
		try {
			int result = service.updateMyCarInfo(map);
			msg = result>0?"차량정보 수정완료":"차량정보 수정실패";
		} catch (IllegalAccessException e) {
			msg = e.getMessage();
		} catch(Exception e) {
			e.printStackTrace();
			msg = "오류발생! 고객센터에 문의바랍니다.";
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.setViewName("mypage/mypageMsg");
		return mav;
	}
	
	@PostMapping("/findCarModelName")
	public String findCarModelName(@RequestParam("fkey") String fkey, @RequestParam("fvalue") String fvalue,
									Model model) throws Exception {
		List<Car_TypeDTO> list = service.findCarModelName(fkey, fvalue);
		model.addAttribute("carList", list);
		return "mypage/toFindCarPopup";
	}
	
}
