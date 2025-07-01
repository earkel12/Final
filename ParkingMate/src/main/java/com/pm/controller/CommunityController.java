package com.pm.controller;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.pm.com.model.CommentDTO;
import com.pm.com.model.CommunityDTO;
import com.pm.com.model.ReviewDTO;
import com.pm.com.service.CommunityService;
import com.pm.page.PageModule;

import jakarta.servlet.http.HttpSession;

@Controller
public class CommunityController {

	@Autowired
	private CommunityService service;

	@GetMapping("/community")
	public String getPmMain() {
		return "/com/main";
	}

	@GetMapping("/comList")
	public ModelAndView showCommunity(HttpSession session, @RequestParam(value = "cp", defaultValue = "1") int cp,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "keyword", required = false) String keyword) throws Exception {

		int listSize = 5;
		int pageSize = 5;

		List<CommunityDTO> arr;
		int totalCnt;

		if (type != null && keyword != null && !keyword.trim().isEmpty()) {
			totalCnt = service.getSearchCount(type, keyword);
			arr = service.searchCommunity(type, keyword, cp, listSize);
		} else {
			totalCnt = service.getTotalCnt();
			arr = service.getCommunityList(cp, listSize);
		}

		String baseUrl = "/comList";
		if (type != null && keyword != null && !keyword.trim().isEmpty()) {
			baseUrl += "?type=" + type + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
		}

		String pageStr = PageModule.makePaging(baseUrl, totalCnt, listSize, pageSize, cp);

		ModelAndView mav = new ModelAndView();
		String userid = (String) session.getAttribute("sid");

		mav.addObject("arr", arr);
		mav.addObject("userid", userid);
		mav.addObject("pageStr", pageStr);
		mav.addObject("type", type);
		mav.addObject("keyword", keyword);
		mav.setViewName("/com/comList");

		return mav;
	}

	@GetMapping("/communityWrite")
	public ModelAndView getWriteForm(HttpSession session) {
		String userid = (String) session.getAttribute("sid");

		if (userid == null || userid.isEmpty()) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("msg", "로그인 후 이용 가능합니다.");
			mav.addObject("gourl", "/login");
			mav.setViewName("com/comMsg");
			return mav;
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("com/communityWrite");
		mav.addObject("communityDTO", new CommunityDTO()); 
		mav.addObject("userid", userid); 
		return mav;
	}

	@PostMapping("/communityWrite")
	public ModelAndView submitPmNotice(HttpSession session, @ModelAttribute CommunityDTO dto) throws Exception {
		String userid = (String) session.getAttribute("sid");
		dto.setId(userid);
		dto.setWritedate(new Date());

		int result = service.insertCommunity(dto);
		ModelAndView mav = new ModelAndView();
		if (result > 0) {
			 mav.setViewName("redirect:/comList");
			    return mav;
		} else {
			mav.addObject("msg", "커뮤니티 글쓰기 실패");
			mav.addObject("gourl", "/communityWrite");
			mav.setViewName("com/comMsg");
			return mav;
		}
	}

	@GetMapping("/community/good")
	public String increaseGood(@RequestParam("idx") int idx) throws Exception {
		service.increaseGood(idx);
		return "redirect:/comment?idx=" + idx;
	}

	@GetMapping("/community/bad")
	public String increaseBad(@RequestParam("idx") int idx) throws Exception {
		service.increaseBad(idx);
		return "redirect:/comment?idx=" + idx;
	}

	@GetMapping("/community/edit")
	public ModelAndView editForm(HttpSession session, @RequestParam("idx") int idx) throws Exception {
		CommunityDTO dto = service.getCommunityIdx(idx);
		String userid = (String) session.getAttribute("sid");

		if (userid == null || !userid.equals(dto.getId())) {
			ModelAndView mav = new ModelAndView();
			mav.addObject("msg", "수정 권한이 없습니다.");
			mav.addObject("gourl", "/comList");
			mav.setViewName("com/comMsg");
			return mav;
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", dto);
		mav.setViewName("/com/commentEdit");
		return mav;
	}

	@PostMapping("/community/edit")
	public ModelAndView editSubmit(HttpSession session, @ModelAttribute CommunityDTO dto) throws Exception {
		CommunityDTO origin = service.getCommunityIdx(dto.getIdx());
		String userid = (String) session.getAttribute("sid");
		ModelAndView mav = new ModelAndView();

		if (userid == null || !userid.equals(origin.getId())) {
			mav.addObject("msg", "수정 권한이 없습니다.");
			mav.addObject("gourl", "/comList");
			mav.setViewName("com/comMsg");
			return mav;
		}

		service.updateCommunity(dto);
		mav.setViewName("redirect:/comment?idx=" + dto.getIdx());
		return mav;
	}

	@GetMapping("/community/delete")
	public ModelAndView delete(HttpSession session, @RequestParam("idx") int idx) throws Exception {
		CommunityDTO dto = service.getCommunityIdx(idx);
		String userid = (String) session.getAttribute("sid");
		ModelAndView mav = new ModelAndView();

		if (userid == null || !userid.equals(dto.getId())) {
			mav.addObject("msg", "수정 권한이 없습니다.");
			mav.addObject("gourl", "/comList");
			mav.setViewName("com/comMsg");
			return mav;
		}

		service.deleteCommunity(idx);
		mav.setViewName("redirect:/comList");
		return mav;
	}

	@GetMapping("/comment")
	public ModelAndView showComment(@RequestParam("idx") int idx) throws Exception {
		service.increaseReadnum(idx);

		CommunityDTO dto = service.getCommunityIdx(idx);
		List<CommentDTO> comments = service.getCommentsByCommunityIdx(idx);

		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", dto);
		mav.addObject("comments", comments);
		mav.setViewName("/com/comment");
		return mav;
	}

	@PostMapping("/comment/write")
	public String writeComment(@ModelAttribute CommentDTO comment, HttpSession session) throws Exception {
		String userid = (String) session.getAttribute("sid");
		if (userid == null)
			return "redirect:/login";

		comment.setId(userid);
		comment.setLev(0);
		comment.setRef(0);
		comment.setSunbun(0);
		service.insertComment(comment);

		return "redirect:/comment?idx=" + comment.getIdx();
	}

	@GetMapping("/comReview")
	public ModelAndView showReview(HttpSession session, @RequestParam(value = "cp", defaultValue = "1") int cp)
			throws Exception {

		int listSize = 5;
		int pageSize = 5;
		int totalCnt = service.getTotalCnt();

		List<ReviewDTO> arr = service.getReviewList(cp, listSize);
		String pageStr = PageModule.makePaging("/comReview", totalCnt, listSize, pageSize, cp);

		ModelAndView mav = new ModelAndView();
		String userid = (String) session.getAttribute("sid");
		mav.addObject("arr", arr);
		mav.addObject("userid", userid);
		mav.addObject("pageStr", pageStr);
		mav.setViewName("/com/comReview");

		return mav;
	}

}
