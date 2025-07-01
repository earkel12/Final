package com.pm.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.*;
import com.pm.ask.model.AskDTO;

@Mapper
public interface AskMapper {

	public int askInsert(AskDTO dto) throws Exception;
	public List<AskDTO> getAskList() throws Exception;
	public List<AskDTO> getAskComment(int idx) throws Exception;
	public int askUpdate(AskDTO dto) throws Exception;
	public List<AskDTO> accomplishedAskList() throws Exception;
	public List<AskDTO> resultContent(int idx) throws Exception;
	public int getTotalCnt();
	public int getTotalCnt2();
}
