package com.pm.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.ModelAndView;

import com.pm.notice.model.NoticeDTO;
import com.pm.notice.model.NoticePotoDTO;
import com.pm.notice.service.NoticeService;

import com.pm.page.PageModule;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService service;

	@GetMapping("/notice")

	public ModelAndView noticeForm(HttpSession session,
			@RequestParam(value = "cp", defaultValue = "1") int cp) throws Exception{

		int listSize = 5;
		int pageSize = 5;
		int totalCnt = service.getTotalCnt();

		List<NoticeDTO> arr = service.getAllNotice(cp, listSize);
		String pageStr = PageModule.makePaging("/notice", totalCnt, listSize, pageSize, cp);

		ModelAndView mav = new ModelAndView();
		String userid = (String) session.getAttribute("sid");
		mav.addObject("arr", arr);
		mav.addObject("userid", userid);
		mav.addObject("pageStr", pageStr);
		mav.setViewName("notice/list");

		return mav;
	}



	public String noticeForm(Model model) {
		try {
			List<NoticeDTO> list = service.getAllNotice();
			model.addAttribute("noticeList", list);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/notice/list";
	}


	@GetMapping("/write")
	public String wirteForm() {
		return "/notice/write";
	}

	@PostMapping("/write")
	public ModelAndView write(NoticeDTO dto) {

		String msg = null;
		dto.setId("관리자");
		try {
			int result = service.noticeInsert(dto);
			msg = result > 0 ? "공지사항 등록 성공" : "공지사항 등록 실패";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.setViewName("notice/noticeMsg");
		return mav;
	}

	@GetMapping("/pm/notice")

	public ModelAndView showPmNotice(HttpSession session, @RequestParam(value = "cp", defaultValue = "1") int cp)
			throws Exception {


		int listSize = 5;
		int pageSize = 5;
		int totalCnt = service.getPmTotalCnt();

		List<NoticeDTO> arr = service.getPmNotice(cp, listSize);
		String pageStr = PageModule.makePaging("/pm/notice", totalCnt, listSize, pageSize, cp);

		ModelAndView mav = new ModelAndView();
		String userid = (String) session.getAttribute("sid");
		mav.addObject("arr", arr);
		mav.addObject("userid", userid);
		mav.addObject("pageStr", pageStr);
		mav.setViewName("pm/notice");

		return mav;
	}

	@GetMapping("/pm/pmNoticeWrite")
	public String pmNoticeWrite() {
		return "/pm/pmNoticeWrite";
	}

	@PostMapping("/pm/pmNoticeWrite")
	public ModelAndView submitPmNotice(@ModelAttribute NoticeDTO dto,
			@RequestPart(value = "photos", required = false) MultipartFile[] photos) throws Exception {

		dto.setId("admin");
		dto.setWritedate(new Date());
		dto.setReadnum(0);
		dto.setDivision(1);

		String msg = null;
		int result = service.insertPmNotice(dto);

		if (result > 0) {
			String[] photoPaths = new String[4];
			for (int i = 0; i < 4; i++) {
				if (photos != null && i < photos.length && !photos[i].isEmpty()) {
					photoPaths[i] = saveFile(photos[i]);
				} else {
					photoPaths[i] = null;
				}
			}

			NoticePotoDTO potoDto = new NoticePotoDTO();
			potoDto.setNoticeNum(dto.getIdx());
			potoDto.setPoto1(photoPaths[0]);
			potoDto.setPoto2(photoPaths[1]);
			potoDto.setPoto3(photoPaths[2]);
			potoDto.setPoto4(photoPaths[3]);

			service.insertPmNoticePoto(potoDto);
			msg = "공지사항 등록에 성공하셨습니다.";

		} else {
			msg = "공지사항 등록에 실패하셨습니다.";
		}

		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.addObject("gourl", "/pm/notice");
		mav.setViewName("pm/pmMsg");
		return mav;
	}

	
	@GetMapping("/content")
	public ModelAndView contentSelect(int idx, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView mav = new ModelAndView();
	    String userid = (String)session.getAttribute("sid");
	    
	    try {
	        String cookieName = "noticeRead_" + idx;
	        boolean hasRead = false;

	        if (request.getCookies() != null) {
	            for (Cookie cookie : request.getCookies()) {
	                if (cookieName.equals(cookie.getName())) {
	                    hasRead = true;
	                    break;
	                }
	            }
	        }
	        if (!hasRead) {
	            service.readnumUpdate(idx); 
	            Cookie cookie = new Cookie(cookieName, "true");
	            cookie.setMaxAge(24 * 60 * 60);
	            cookie.setPath("/");
	            response.addCookie(cookie);
	        }
	        
	        NoticeDTO dto = service.contentSelect(idx);
	        mav.addObject("userid", userid);
	        mav.addObject("dto", dto);
	        mav.setViewName("notice/content");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return mav;
	}

	@GetMapping("/delete")
	public ModelAndView noticeDelete(int idx) {
		ModelAndView mav = new ModelAndView();
		String msg = null;
		String url = "/notice";
		try {
			int result = service.noticeDelete(idx);
			msg = result > 0 ? "삭제 성공":"삭제 실패";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		mav.addObject("msg",msg);
		mav.addObject("gourl", url);
		mav.setViewName("notice/noticeMsg");
		return mav;
	}

	@GetMapping("/edit")
	public String eidtForm(int idx, Model model) {
		try {
			NoticeDTO dto = service.contentSelect(idx);
			model.addAttribute("dto",dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/notice/edit";
	}
	 @Value("${file.upload-dir}")
	 private String uploadDir;
	 
	// 파일 업로드 메서드
	private String saveFile(MultipartFile file) throws IOException {
		if (file == null || file.isEmpty()) {
			return null;
		}
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String originalFilename = file.getOriginalFilename();
		String savedFilename = UUID.randomUUID().toString() + "_" + originalFilename;
		File dest = new File(uploadDir + savedFilename);
		file.transferTo(dest);
		return "/upload/" + savedFilename;
	}

	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
		Path file = Paths.get(uploadDir).resolve(filename);
		if (!Files.exists(file)) {
			return ResponseEntity.notFound().build();
		}
		Resource resource = new UrlResource(file.toUri());

		String contentType = "application/octet-stream";
		try {
			contentType = Files.probeContentType(file);
		} catch (IOException e) {
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}

	@PostMapping("/edit")
	public ModelAndView edit(NoticeDTO dto) {
		ModelAndView mav = new ModelAndView();
		String msg = null;
		try {
			int result = service.noticeUpdate(dto);
			msg = result > 0 ? "수정 성공":"수정 실패";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mav = new ModelAndView();
		mav.addObject("msg", msg);
		mav.setViewName("notice/noticeMsg");
		return mav;
	}
}