package com.pm.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pm.member.model.MemberDTO;

@Mapper
public interface RegisterMapper {

	public int registerForm(MemberDTO dto) throws Exception;
	
	public String checkId(String id) throws Exception;
	
	public MemberDTO selectUserById(String id) throws Exception;
	
	public int registerFormUpdate(Map<String, Object> map) throws Exception;
}
