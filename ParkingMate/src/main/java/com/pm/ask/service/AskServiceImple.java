package com.pm.ask.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.ask.model.AskDTO;
import com.pm.mapper.AskMapper;

@Service
public class AskServiceImple implements AskService {

	@Autowired
	private AskMapper mapper;
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int askInsert(AskDTO dto) throws Exception {
		int count = mapper.askInsert(dto);
		return count;
	}
	
	@Override
	public List<AskDTO> getAskList(int cp, int ls) throws Exception {
		int start = (cp - 1) * ls;
		int end = ls;

		Map<String, Integer> paramMap = new HashMap<>();
		paramMap.put("start", start);
		paramMap.put("end", end);
		
		return sqlSessionTemplate.selectList("com.pm.mapper.AskMapper.getAskList", paramMap);
	}
	
	@Override
	public List<AskDTO> getAskComment(int idx) throws Exception {
		return mapper.getAskComment(idx);
	}
	
	@Override
	public int askUpdate(AskDTO dto) throws Exception {
		return mapper.askUpdate(dto);
	}
	
	@Override
	public List<AskDTO> accomplishedAskList(int cp, int ls) throws Exception {
		int start = (cp - 1) * ls;
		int end = ls;

		Map<String, Integer> paramMap = new HashMap<>();
		paramMap.put("start", start);
		paramMap.put("end", end);

		return sqlSessionTemplate.selectList("com.pm.mapper.AskMapper.accomplishedAskList", paramMap);
	}

	@Override
	public List<AskDTO> resultContent(int idx) throws Exception {
		return mapper.resultContent(idx);
	}
	
	@Override
		public int getTotalCnt() {
			return mapper.getTotalCnt();
		}
	
	@Override
	public int getTotalCnt2() {
		return mapper.getTotalCnt2();
	}
	@Override
	public List<AskDTO> myAskInfo(String id) throws Exception {
		return mapper.myAskInfo(id);
	}
}
