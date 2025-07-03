package com.pm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pm.parkingLotRegister.model.ParkingLotRegisterDTO;
import com.pm.parkingLotRegister.service.ParkingLotRegisterService;

@Controller
public class ParkingLotRegisterController {

	@Autowired
	private ParkingLotRegisterService service;
	
	@GetMapping("/parkingLotRegister")
	public String parkingLotRegisterForm() {
		return "parkinglot/parkingLotRegister";
	}
	
	@PostMapping("/parkingLotInsert")
	public ModelAndView parkingLotRegister(ParkingLotRegisterDTO dto) {
		String msg = null;
		String goUrl = "/parkingLotRegister";
		try {
			int result = service.parkingLotInsert(dto);
			msg = result > 0 ? "주차장 등록 성공" : "주차장 등록 실패";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.addObject("goUrl", goUrl);
		mav.setViewName("parkinglot/parkingLotMsg");
		return mav;
	}
	
	@PostMapping("/parkingLotDelete")
	public ModelAndView parkingLotDelete(String name) {
		String msg = null;
		String goUrl = "/parkingLotRegister";
		try {
			int result = service.parkingLotDelete(name);
			msg = result > 0 ? "삭제완료" : "삭제실패";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.addObject("goUrl", goUrl);
		mav.setViewName("parkinglot/parkingLotMsg");
		return mav;
	}
	
	@PostMapping("/parkingLotUpdate")
	public ModelAndView parkingLotUpdate(ParkingLotRegisterDTO dto) {
		String msg = null;
		String goUrl = "/parkingLotRegister";
		try {
			int result = service.parkingLotUpdate(dto);
			msg = result > 0 ? "수정완료" : "수정실패";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.addObject("goUrl", goUrl);
		mav.setViewName("parkinglot/parkingLotMsg");
		return mav;
	}
	
	   // 이름 중복 검사 API (Ajax용)
    @GetMapping("/parkingLot/checkName")
    @ResponseBody
    public String checkName(@RequestParam String name) throws Exception{
        boolean available = service.checkName(name);
        return available ? "ok" : "no";
    }
}
