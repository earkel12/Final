package com.pm.ask.service;

import java.util.List;

import com.pm.ask.model.AskDTO;

public interface AskService {
	
	public int askInsert(AskDTO dto) throws Exception;
	public List<AskDTO> getAskList(int cp, int ls) throws Exception;
	public List<AskDTO> getAskComment(int idx) throws Exception;
	public int askUpdate(AskDTO dto) throws Exception;
	public List<AskDTO> accomplishedAskList(int cp, int ls) throws Exception;
	public List<AskDTO> resultContent(int idx) throws Exception;
	public int getTotalCnt();
	public int getTotalCnt2();
	public List<AskDTO> myAskInfo(String id) throws Exception;
}
