package com.pm.parkingLotRegister.service;

import com.pm.parkingLotRegister.model.ParkingLotRegisterDTO;

public interface ParkingLotRegisterService {
	
	public int parkingLotInsert(ParkingLotRegisterDTO dto) throws Exception;
	public int parkingLotDelete(String name) throws Exception;
	public int parkingLotUpdate(ParkingLotRegisterDTO dto) throws Exception;
	public boolean checkName(String name) throws Exception;

}
