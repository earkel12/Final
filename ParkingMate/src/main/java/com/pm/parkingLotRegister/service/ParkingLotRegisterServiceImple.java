package com.pm.parkingLotRegister.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.mapper.ParkingLotRegisterMapper;
import com.pm.parkingLotRegister.model.ParkingLotRegisterDTO;

@Service
public class ParkingLotRegisterServiceImple implements ParkingLotRegisterService {
	
	@Autowired
	private ParkingLotRegisterMapper mapper;
	
	@Override
	public int parkingLotInsert(ParkingLotRegisterDTO dto) throws Exception {
		return mapper.parkingLotInsert(dto);
	}

	@Override
	public int parkingLotDelete(String name) throws Exception {
		return mapper.parkingLotDelete(name);
	}
	
	@Override
	public int parkingLotUpdate(ParkingLotRegisterDTO dto) throws Exception {
		return mapper.parkingLotUpdate(dto);
	}
	
	@Override
	public boolean checkName(String name) throws Exception {
		int count = mapper.checkName(name);
		return count == 0;
	}
}
