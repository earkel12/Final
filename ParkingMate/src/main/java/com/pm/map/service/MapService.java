package com.pm.map.service;

import java.util.List;

import com.pm.map.model.ParkingLotDTO;
import java.util.*;

public interface MapService {

	public List<ParkingLotDTO> plInfo() throws Exception;
	public ParkingLotDTO plbyname(String name) throws Exception;
	public List<ParkingLotDTO> searchPl(String name) throws Exception;
}
