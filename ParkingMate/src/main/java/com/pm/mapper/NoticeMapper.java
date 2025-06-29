package com.pm.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pm.notice.model.NoticeDTO;

@Mapper
public interface NoticeMapper {
	public int noticeInsert(NoticeDTO dto) throws Exception;
	public List<NoticeDTO> getAllNotices() throws Exception;
	public NoticeDTO contentSelect(int idx) throws Exception;
	public int noticeDelete(int idx) throws Exception;
	public int noticeUpdate(NoticeDTO dto) throws Exception;
	public List<NoticeDTO> getPmNotice() throws Exception;
	public int insertPmNotice(NoticeDTO dto) throws Exception;
	public int getPmMaxRef();
	public int getPmTotalCnt();
	public NoticeDTO getContent(int idx);
	public int getTotalCnt();

}
