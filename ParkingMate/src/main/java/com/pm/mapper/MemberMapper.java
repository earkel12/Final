package com.pm.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	public String loginCheck(String id) throws Exception;
	public String userInfo(String id) throws Exception;
	//booking 시 이용자 정보 조회
	public String selectTelById(String id) throws Exception;
}
