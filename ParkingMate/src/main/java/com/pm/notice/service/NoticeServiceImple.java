package com.pm.notice.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.mapper.NoticeMapper;
import com.pm.notice.model.NoticeDTO;
import com.pm.notice.model.NoticePotoDTO;

@Service
public class NoticeServiceImple implements NoticeService {

	@Autowired
	private NoticeMapper mapper;

	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	

	@Override
	public int noticeInsert(NoticeDTO dto) throws Exception {
		int count = mapper.noticeInsert(dto);
		return count;
	}

	@Override
	public List<NoticeDTO> getAllNotice(int cp, int ls) throws Exception {
		int start = (cp - 1) * ls;
		int end = ls;

		Map<String, Integer> paramMap = new HashMap<>();
		paramMap.put("start", start);
		paramMap.put("end", end);

		return sqlSessionTemplate.selectList("com.pm.mapper.NoticeMapper.getAllNotices", paramMap);
	}

	@Override
	public NoticeDTO contentSelect(int idx) throws Exception {
		return mapper.contentSelect(idx);
	}

	@Override
	public int noticeDelete(int idx) throws Exception {
		return mapper.noticeDelete(idx);
	}

	@Override
	public int noticeUpdate(NoticeDTO dto) throws Exception {
		int count = mapper.noticeUpdate(dto);
		return count;
	}
	
	@Override
	public List<NoticeDTO> getAllNotice() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

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
    public int insertPmNoticePoto(NoticePotoDTO potoDto) throws Exception {
        return mapper.insertPmNoticePoto(potoDto);
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

	@Override
	public int getTotalCnt() {
		int totalCnt = sqlSessionTemplate.selectOne("com.pm.mapper.NoticeMapper.getTotalCnt");
		return totalCnt == 0 ? 1 : totalCnt;
	}

}