package com.pm.map.service;

import java.util.*;

import com.pm.map.model.ParkingLotDTO;

public interface MapService {

	public ParkingLotDTO plInfo(String name) throws Exception;
}
