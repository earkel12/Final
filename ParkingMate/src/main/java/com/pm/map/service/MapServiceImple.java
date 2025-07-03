package com.pm.map.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.pm.map.model.ParkingLotDTO;
import com.pm.mapper.MapMapper;
import java.util.*;

@Service
public class MapServiceImple implements MapService {

	@Autowired
	private MapMapper mapper;

	@Override
	public List<ParkingLotDTO> plInfo() throws Exception {
		
		return mapper.plInfo();
	}
	@Override
	public ParkingLotDTO plbyname(String name) throws Exception {
		
		return mapper.plbyname(name);
	}
	
	@Override
	public List<ParkingLotDTO> searchPl(String name) throws Exception {
		
		return mapper.searchPl(name);
	}
}
