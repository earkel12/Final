package com.pm.member.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.mapper.MemberMapper;

@Service
public class MemberServiceImple implements MemberService {

	@Autowired
	private MemberMapper mapper;
	
	@Override
	public int loginCheck(String userid, String userpwd) throws Exception {
		String dbpwd = mapper.loginCheck(userid);
		int result = 0;
		if(dbpwd == null) {
			result = NOT_ID;
		}else {
			if(dbpwd.equals(userpwd)) {
				result = LOGIN_OK;
			}else {
				result = NOT_PWD;
			}
		}
		return result;
	}
	
@Override
public String userInfo(String userid) throws Exception {
	String dbname = mapper.userInfo(userid);
	
	return dbname;
}
}
