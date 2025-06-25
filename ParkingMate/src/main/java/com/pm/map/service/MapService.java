package com.pm.map.service;

import java.util.*;

import com.pm.map.model.ParkingLotDTO;

public interface MapService {

	public List<ParkingLotDTO> plInfo() throws Exception;
	public ParkingLotDTO plbyname(String name) throws Exception;
}
