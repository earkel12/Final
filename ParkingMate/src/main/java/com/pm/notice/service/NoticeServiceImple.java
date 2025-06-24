package com.pm.notice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.mapper.NoticeMapper;
import com.pm.notice.model.NoticeDTO;

@Service
public class NoticeServiceImple implements NoticeService {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Autowired
	private NoticeMapper mapper;

	@Override
	public List<NoticeDTO> getPmNotice(int cp, int ls) throws Exception {
		int start = (cp - 1) * ls;
		int end = ls;

		Map<String, Integer> paramMap = new HashMap<>();
		paramMap.put("start", start);
		paramMap.put("end", end);

		return sqlSessionTemplate.selectList("com.pm.mapper.NoticeMapper.getPmNotice", paramMap);
	}

	@Override
	public int insertPmNotice(NoticeDTO dto) throws Exception {
		return mapper.insertPmNotice(dto);
	}

	@Override
	public int getPmMaxRef() {
		Integer maxRef = sqlSessionTemplate.selectOne("com.pm.mapper.NoticeMapper.getPmMaxRef");
		if (maxRef == null) {
			return 0;
		}
		return maxRef;
	}

	@Override
	public int getPmTotalCnt() {
		int totalCnt = sqlSessionTemplate.selectOne("com.pm.mapper.NoticeMapper.getPmTotalCnt");
		return totalCnt == 0 ? 1 : totalCnt;
	}

	@Override
	public NoticeDTO getContent(int idx) {
		NoticeDTO dto = sqlSessionTemplate.selectOne("com.pm.mapper.NoticeMapper.getContent", idx);
		return dto;
	}
}
