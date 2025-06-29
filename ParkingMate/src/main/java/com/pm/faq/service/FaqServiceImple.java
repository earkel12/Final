package com.pm.faq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.faq.model.FaqDTO;
import com.pm.mapper.FaqMapper;

@Service
public class FaqServiceImple implements FaqService {

	@Autowired
	private FaqMapper mapper;

	@Override
	public int faqInsert(FaqDTO dto) throws Exception {
		int count = mapper.faqInsert(dto);
		return count;
	}

	@Override
	public List<FaqDTO> faqSelect() throws Exception {
		return mapper.faqSelect();
	}

	@Override
	public FaqDTO faqEditSel(int idx) throws Exception {
		return mapper.faqEditSel(idx);
	}

	@Override
		public int faqUpdate(FaqDTO dto) throws Exception {
			return mapper.faqUpdate(dto);
		}

	@Override
	public int faqDelete(int idx) throws Exception {
		return mapper.faqDelete(idx);
	}

}
