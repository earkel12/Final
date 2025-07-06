package com.pm.register.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.ui.Model;

import com.pm.member.model.MemberDTO;

public interface RegisterService {

	public int registerForm(MemberDTO dto) throws Exception;

	public boolean checkId(String id) throws Exception;
	
	public MemberDTO selectUserById(String id) throws Exception;
	
	public int registerFormUpdate(Map<String, Object> map) throws Exception;

	public int pwdUpdate(String id, String currentPwd, String pwd) throws Exception;
	
}
