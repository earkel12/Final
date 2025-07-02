package com.pm.parkingLotRegister.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.mapper.ParkingLotRegisterMapper;

@Service
public class ParkingLotRegisterServiceImple implements ParkingLotRegisterService {
	
	@Autowired
	private ParkingLotRegisterMapper mapper;

}
