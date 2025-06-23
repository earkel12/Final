package com.pm.notice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.mapper.NoticeMapper;

@Service
public class NoticeServiceImple implements NoticeService {
	@Autowired
	private NoticeMapper mapper;
}
