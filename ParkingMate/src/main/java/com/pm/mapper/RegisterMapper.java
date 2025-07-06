package com.pm.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pm.member.model.MemberDTO;

@Mapper
public interface RegisterMapper {

	public int registerForm(MemberDTO dto) throws Exception;

	public String checkId(String id) throws Exception;

	public MemberDTO selectUserById(String id) throws Exception;
	
	public int registerFormUpdate(Map<String, Object> map) throws Exception;
	
	public String findPwdById(String id) throws Exception;
	public int pwdUpdate(@Param("id") String id, @Param("pwd") String pwd) throws Exception;

}
