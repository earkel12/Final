package com.pm.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.pm.member.model.MemberDTO;

@Mapper
public interface RegisterMapper {

	public int registerForm(MemberDTO dto) throws Exception;
	
}
