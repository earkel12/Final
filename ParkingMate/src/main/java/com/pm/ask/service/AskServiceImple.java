package com.pm.ask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.ask.model.AskDTO;
import com.pm.mapper.AskMapper;

@Service
public class AskServiceImple implements AskService {

	@Autowired
	private AskMapper mapper;
	
	@Override
	public int askInsert(AskDTO dto) throws Exception {
		int count = mapper.askInsert(dto);
		return count;
	}
}
