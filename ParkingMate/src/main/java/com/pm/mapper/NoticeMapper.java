package com.pm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.servlet.ModelAndView;

import com.pm.notice.model.NoticeDTO;
import java.util.*;

@Mapper
public interface NoticeMapper {

	public int noticeInsert(NoticeDTO dto) throws Exception;
	public List<NoticeDTO> getALlNotices() throws Exception;
}
