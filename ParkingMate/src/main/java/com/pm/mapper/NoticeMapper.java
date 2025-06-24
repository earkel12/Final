package com.pm.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.servlet.ModelAndView;

import com.pm.notice.model.NoticeDTO;
import java.util.*;

@Mapper
public interface NoticeMapper {
  public int noticeInsert(NoticeDTO dto) throws Exception;
	public List<NoticeDTO> getAllNotices() throws Exception;
	public List<NoticeDTO> getPmNotice() throws Exception;
	public int insertPmNotice(NoticeDTO dto) throws Exception;
	public int getPmMaxRef();
	public int getPmTotalCnt();
	public NoticeDTO getContent(int idx);

}
