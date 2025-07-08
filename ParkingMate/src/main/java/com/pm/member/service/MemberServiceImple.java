package com.pm.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.booking.model.BookingDTO;
import com.pm.mapper.MemberMapper;
import com.pm.member.model.MemberDTO;

@Service
public class MemberServiceImple implements MemberService {

	@Autowired
	private MemberMapper mapper;

	@Override
	public int loginCheck(String userid, String userpwd) throws Exception {
		String dbpwd = mapper.loginCheck(userid);
		int result = 0;
		if (dbpwd == null) {
			result = NOT_ID;
		} else {
			if (dbpwd.equals(userpwd)) {
				result = LOGIN_OK;
			} else {
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

@Override
public String idFind(MemberDTO dto) throws Exception {
	return mapper.idFind(dto);
}

@Override
public String pwdFind(MemberDTO dto) throws Exception {
	return mapper.pwdFind(dto);
}

	
	@Override
	public String getTelById(String id) throws Exception{
	    return mapper.selectTelById(id);
	}
	
	
	//관리자모드-회원리스트
	@Override
	public List<Map<String, Object>> checkMemberListByAdmin() throws Exception {
		return mapper.checkMemberListByAdmin();
	}
	//관리자모드-특정회원예약내역
	@Override
	public List<Map<String, Object>> findBookingListById(String id) throws Exception {
		return mapper.findBookingListById(id);
	}
	

}
