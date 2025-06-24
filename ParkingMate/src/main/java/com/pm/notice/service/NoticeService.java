package com.pm.notice.service;

import java.util.List;

import com.pm.notice.model.NoticeDTO;

public interface NoticeService {
	public List<NoticeDTO> getPmNotice(int cp, int ls) throws Exception;
	public int insertPmNotice(NoticeDTO dto) throws Exception;
	public int getPmMaxRef();
	public int getPmTotalCnt();
	public NoticeDTO getContent(int idx);
}
