package com.pm.register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.mapper.RegisterMapper;
import com.pm.member.model.MemberDTO;

@Service
public class RegisterServiceImple implements RegisterService {
	
	@Autowired
	private RegisterMapper mapper;
	
	@Override
	public int registerForm(MemberDTO dto) throws Exception {
		
		int count = mapper.registerForm(dto);
		return count;
		
		
	}

}
