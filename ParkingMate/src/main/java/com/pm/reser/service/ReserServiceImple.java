package com.pm.reser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import com.pm.mapper.ReserMapper;
import com.pm.reser.model.ReserDTO;
import com.pm.reser.model.UserCarDTO;


@Service
public class ReserServiceImple implements ReserService {
	
	@Autowired
	private ReserMapper mapper;
	
	@Override
	public void insertReser(ReserDTO booking) throws Exception {
		mapper.insertReser(booking);
	}
	
	@Override
	public List<UserCarDTO> carbyid(String userid) throws Exception {
		
		return mapper.carbyid(userid);
	}
}
