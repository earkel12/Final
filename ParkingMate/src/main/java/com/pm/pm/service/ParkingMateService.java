package com.pm.pm.service;

import java.util.List;

import com.pm.pm.model.MatePayCheckDTO;
import com.pm.pm.model.ParkingMateDTO;

public interface ParkingMateService {
	public ParkingMateDTO getParkingMate(String id) throws Exception;
	public int insertParkingMate(ParkingMateDTO dto) throws Exception;
	public List<MatePayCheckDTO> getMatePayCheck() throws Exception;
}
