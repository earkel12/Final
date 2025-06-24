package com.pm.notice.service;

import com.pm.notice.model.NoticeDTO;

import java.util.*;

public interface NoticeService {
	
	public int noticeInsert(NoticeDTO dto)throws Exception;
	public List<NoticeDTO> getAllNotice() throws Exception;

}
