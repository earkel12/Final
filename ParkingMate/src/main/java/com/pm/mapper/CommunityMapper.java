package com.pm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pm.com.model.CommentDTO;
import com.pm.com.model.CommunityDTO;
import com.pm.com.model.ReviewDTO;

@Mapper
public interface CommunityMapper {
	public List<CommunityDTO> getCommunityList(int cp, int ls) throws Exception;
	public int insertCommunity(CommunityDTO dto) throws Exception;
	public int insertComment(CommentDTO dto) throws Exception;
	public CommunityDTO getCommunityIdx(int idx) throws Exception;
	public int updateReadnum(int idx) throws Exception;
	public int getPmTotalCnt();
	public int increaseGood(int idx) throws Exception;
	public int increaseBad(int idx) throws Exception;
	public int updateCommunity(CommunityDTO dto) throws Exception;
	public int deleteCommunity(int idx) throws Exception;
	public int deleteCommentsByCommunityIdx(int idx) throws Exception;
	public List<CommentDTO> getCommentsByCommunityIdx(int idx) throws Exception;
	public List<ReviewDTO> getReviewList(int cp, int ls) throws Exception;
	public int getSearchCount(String type, String keyword);
	public List<CommunityDTO> searchCommunity(String type, String keyword, int cp, int listSize);
}
