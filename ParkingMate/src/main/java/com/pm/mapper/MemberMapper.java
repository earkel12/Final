package com.pm.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import com.pm.member.model.MemberDTO;

@Mapper
public interface MemberMapper {

	public String loginCheck(String id) throws Exception;
	public String userInfo(String id) throws Exception;
}
