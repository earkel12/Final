package com.pm.notice.service;



import java.util.List;

import com.pm.notice.model.NoticeDTO;
import com.pm.notice.model.NoticePotoDTO;




public interface NoticeService {
  public int noticeInsert(NoticeDTO dto)throws Exception;
	public List<NoticeDTO> getAllNotice(int cp, int ls) throws Exception;
	public NoticeDTO contentSelect(int idx) throws Exception;
	public int noticeDelete(int idx) throws Exception;
	public int noticeUpdate(NoticeDTO dto) throws Exception;

	public int noticeInsert(NoticeDTO dto)throws Exception;
	public List<NoticeDTO> getAllNotice() throws Exception;

	public List<NoticeDTO> getPmNotice(int cp, int ls) throws Exception;
	
	public int insertPmNotice(NoticeDTO dto) throws Exception;
	public int insertPmNoticePoto(NoticePotoDTO potoDto) throws Exception;
	
	public int getPmTotalCnt();
	public NoticeDTO getContent(int idx);
	public int getTotalCnt();
}
