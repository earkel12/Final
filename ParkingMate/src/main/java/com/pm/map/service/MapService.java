package com.pm.map.service;

import com.pm.map.model.ParkingLotDTO;
import java.util.*;

public interface MapService {

	public List<ParkingLotDTO> plInfo() throws Exception;
	public ParkingLotDTO plbyname(String name) throws Exception;
}
