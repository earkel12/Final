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
	public List<NoticeDTO> getPmNotice() throws Exception {
		return mapper.getPmNotice();
	}
	@Override
	public int insertNotice(NoticeDTO dto) throws Exception {
	    return mapper.insertNotice(dto);
	}
}
