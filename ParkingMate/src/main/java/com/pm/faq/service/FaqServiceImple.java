package com.pm.faq.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.pm.faq.model.FaqDTO;

public class FaqServiceImple implements FaqService {

	@Autowired
	private FaqMapper mapper;
	
	@Override
	public int faqInsert(FaqDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
