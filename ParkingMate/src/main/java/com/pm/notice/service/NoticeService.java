package com.pm.notice.service;

import java.util.List;

import com.pm.notice.model.NoticeDTO;

public interface NoticeService {
	public List<NoticeDTO> getPmNotice() throws Exception;
	public int insertNotice(NoticeDTO dto) throws Exception;
}
