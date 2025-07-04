package com.pm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pm.member.model.MemberDTO;

@Mapper
public interface MemberMapper {

	public String loginCheck(String id) throws Exception;
	public String userInfo(String id) throws Exception;

	public String idFind(MemberDTO dto) throws Exception;
	public String pwdFind(MemberDTO dto) throws Exception;
	//booking 시 이용자 정보 조회
	public String selectTelById(String id) throws Exception;

}
