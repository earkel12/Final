package com.pm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.pm.parkingLotRegister.service.ParkingLotRegisterService;

@Controller
public class ParkingLotRegisterController {

	@Autowired
	private ParkingLotRegisterService service;
	
	@GetMapping("/parkingLotRegister")
	public String parkingLotRegisterForm() {
		return "parkinglot/parkingLotRegister";
	}
}
