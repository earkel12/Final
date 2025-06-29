package com.pm.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	public String loginCheck(String id) throws Exception;
	public String userInfo(String id) throws Exception;
}
