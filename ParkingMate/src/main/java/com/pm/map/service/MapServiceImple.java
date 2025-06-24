package com.pm.map.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.map.model.ParkingLotDTO;
import com.pm.mapper.MapMapper;
import com.pm.mapper.MemberMapper;

@Service
public class MapServiceImple implements MapService {

	@Autowired
	private MapMapper mapper;
	
	@Override
	public ParkingLotDTO plInfo(String name) throws Exception {
		
		return mapper.plInfo(name);
	}
	
}
