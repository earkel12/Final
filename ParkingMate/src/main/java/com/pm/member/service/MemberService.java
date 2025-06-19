package com.pm.member.service;

import java.util.*;

import com.pm.member.model.MemberDTO;

public interface MemberService {

	public static int NOT_ID = 1;
	public static int NOT_PWD = 2;
	public static int LOGIN_OK = 3;
	public static int ERROR = -1;
	
	public int loginCheck(String userid, String userpwd) throws Exception;
	public String userInfo(String userid) throws Exception;
}
