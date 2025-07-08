package com.pm.com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.com.model.CommentDTO;
import com.pm.com.model.CommunityDTO;
import com.pm.com.model.ReviewDTO;
import com.pm.mapper.CommunityMapper;

@Service
public class CommunityServiceImple implements CommunityService {
	
	@Autowired
	private CommunityMapper mapper;
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<CommunityDTO> getCommunityList(int cp, int ls) throws Exception {
		int start = (cp - 1) * ls;
		int end = ls;

		Map<String, Integer> paramMap = new HashMap<>();
		paramMap.put("start", start);
		paramMap.put("end", end);

		return sqlSessionTemplate.selectList("com.pm.mapper.CommunityMapper.getCommunityList", paramMap);
	}
	
	@Override
	public int insertCommunity(CommunityDTO dto) throws Exception {
	    return mapper.insertCommunity(dto);
	}
	@Override
	public int insertComment(CommentDTO comment) throws Exception {
		return mapper.insertComment(comment);
	}
	@Override
	public int getTotalCnt() {
		int totalCnt = sqlSessionTemplate.selectOne("com.pm.mapper.CommunityMapper.getTotalCnt");
		return totalCnt == 0 ? 1 : totalCnt;
	}
	@Override
	public CommunityDTO getCommunityIdx(int idx) throws Exception {
		 return mapper.getCommunityIdx(idx);
	}
	@Override
	public int increaseReadnum(int idx) throws Exception {
		 return mapper.updateReadnum(idx);
	}
	@Override
	public int increaseGood(int idx) throws Exception {
	    return mapper.increaseGood(idx);
	}

	@Override
	public int increaseBad(int idx) throws Exception {
	    return mapper.increaseBad(idx);
	}
	@Override
	public int updateCommunity(CommunityDTO dto) throws Exception {
	    return mapper.updateCommunity(dto);
	}

	@Override
	public int deleteCommunity(int idx) throws Exception {
		mapper.deleteCommentsByCommunityIdx(idx);
		return mapper.deleteCommunity(idx);
	}
	@Override
	public List<CommentDTO> getCommentsByCommunityIdx(int idx) throws Exception {
		return mapper.getCommentsByCommunityIdx(idx);
	}
	
	@Override
	public List<ReviewDTO> getReviewList(int cp, int ls) throws Exception {
		int start = (cp - 1) * ls;
		int end = ls;

		Map<String, Integer> paramMap = new HashMap<>();
		paramMap.put("start", start);
		paramMap.put("end", end);

		return sqlSessionTemplate.selectList("com.pm.mapper.CommunityMapper.getReviewList", paramMap);
	}

	
	@Override
	public int insertReview(ReviewDTO dto) throws Exception {
		
		return mapper.insertReview(dto);
	}
	
	@Override
	public ReviewDTO reviewContent(int bookingnum) throws Exception {
		
		return mapper.reviewContent(bookingnum);
	}
	
	@Override
	public int getTotalCnt2() {
		int totalCnt = sqlSessionTemplate.selectOne("com.pm.mapper.CommunityMapper.getTotalCnt2");
		return totalCnt == 0 ? 1 : totalCnt;
	}
	
	
	
	@Override
	public int getSearchCount(String type, String keyword) {
	    Map<String, String> paramMap = new HashMap<>();
	    paramMap.put("type", type);
	    paramMap.put("keyword", "%" + keyword + "%");

	    return sqlSessionTemplate.selectOne("com.pm.mapper.CommunityMapper.getSearchCount", paramMap);
	}

	@Override
	public List<CommunityDTO> searchCommunity(String type, String keyword, int cp, int ls) {
	    int start = (cp - 1) * ls;
	    int end = ls;

	    Map<String, Object> paramMap = new HashMap<>();
	    paramMap.put("type", type);
	    paramMap.put("keyword", "%" + keyword + "%");
	    paramMap.put("start", start);
	    paramMap.put("end", end);

	    return sqlSessionTemplate.selectList("com.pm.mapper.CommunityMapper.searchCommunity", paramMap);

	}
}
