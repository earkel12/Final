package com.pm.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.mapper.NoticeMapper;
import com.pm.notice.model.NoticeDTO;

@Service
public class NoticeServiceImple implements NoticeService {
	@Autowired
	private NoticeMapper mapper;
	
	@Override
	public int noticeInsert(NoticeDTO dto) throws Exception {
		int count = mapper.noticeInsert(dto);
		return count;
	}
	
	@Override
	public List<NoticeDTO> getAllNotice() throws Exception {
		return mapper.getALlNotices();
	}
}
