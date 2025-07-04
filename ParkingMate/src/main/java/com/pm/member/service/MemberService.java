package com.pm.member.service;

public interface MemberService {

	public static int NOT_ID = 1;
	public static int NOT_PWD = 2;
	public static int LOGIN_OK = 3;
	public static int ERROR = -1;

	public int loginCheck(String userid, String userpwd) throws Exception;
	public String userInfo(String userid) throws Exception;
	
	//booking 시 이용자 정보 조회
	public String getTelById(String id) throws Exception;
}
