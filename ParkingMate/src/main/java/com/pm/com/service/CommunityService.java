package com.pm.com.service;

import java.util.List;

import com.pm.com.model.CommentDTO;
import com.pm.com.model.CommunityDTO;
import com.pm.com.model.ReviewDTO;
import com.pm.notice.model.NoticeDTO;

public interface CommunityService {
	public List<CommunityDTO> getCommunityList(int cp, int ls) throws Exception;
	public int insertCommunity(CommunityDTO dto) throws Exception;
	public int insertComment(CommentDTO comment) throws Exception;
	public CommunityDTO getCommunityIdx(int idx) throws Exception;
	public int increaseReadnum(int idx) throws Exception;
	public int increaseGood(int idx) throws Exception;
	public int increaseBad(int idx) throws Exception;
	public int updateCommunity(CommunityDTO dto) throws Exception;
	public int deleteCommunity(int idx) throws Exception;
	public int getTotalCnt();
	public List<CommentDTO> getCommentsByCommunityIdx(int idx) throws Exception;
	public List<ReviewDTO> getReviewList(int cp, int ls) throws Exception;
  public int getSearchCount(String type, String keyword);
	public List<CommunityDTO> searchCommunity(String type, String keyword, int cp, int listSize);
	public int insertReview(ReviewDTO dto) throws Exception;
	public ReviewDTO reviewContent(int bookingnum) throws Exception;

}
