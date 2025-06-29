package com.pm.faq.service;

import java.util.List;

import com.pm.faq.model.FaqDTO;

public interface FaqService {

	public int faqInsert(FaqDTO dto) throws Exception;
	public List<FaqDTO> faqSelect() throws Exception;
	public FaqDTO faqEditSel(int idx) throws Exception;
	public int faqUpdate(FaqDTO dto) throws Exception;
	public int faqDelete(int idx) throws Exception;
}
