package com.pm.notice.service;


import com.pm.notice.model.NoticeDTO;

import java.util.*;


public interface NoticeService {
  public int noticeInsert(NoticeDTO dto)throws Exception;
	public List<NoticeDTO> getAllNotice() throws Exception;
	public List<NoticeDTO> getPmNotice(int cp, int ls) throws Exception;
	public int insertPmNotice(NoticeDTO dto) throws Exception;
	public int getPmMaxRef();
	public int getPmTotalCnt();
	public NoticeDTO getContent(int idx);

}
