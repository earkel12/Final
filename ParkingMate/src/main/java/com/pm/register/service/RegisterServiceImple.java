package com.pm.register.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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

	@Override
	public boolean checkId(String id) throws Exception {
		String result = mapper.checkId(id);
		System.out.println("id체크:"+result);

		return result==null?false:true;
	}
	
	@Override
	public MemberDTO selectUserById(String id) throws Exception {
		MemberDTO dto = mapper.selectUserById(id);
		return dto;
	}
	
	@Override
	public int registerFormUpdate(Map<String, Object> map) throws Exception {
		int count = mapper.registerFormUpdate(map);
		return count;
	}

}
