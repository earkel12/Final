package com.pm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pm.notice.model.NoticeDTO;

@Mapper
public interface NoticeMapper {
	public List<NoticeDTO> getPmNotice() throws Exception;
	public int insertPmNotice(NoticeDTO dto) throws Exception;
	public int getPmMaxRef();
	public int getPmTotalCnt();
	public NoticeDTO getContent(int idx);
}
