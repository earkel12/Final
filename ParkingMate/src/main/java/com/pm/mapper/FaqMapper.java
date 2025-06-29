package com.pm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pm.faq.model.FaqDTO;

@Mapper
public interface FaqMapper {

	public int faqInsert(FaqDTO dto) throws Exception;
	public List<FaqDTO> faqSelect() throws Exception;
	public FaqDTO faqEditSel(int idx) throws Exception;
	public int faqUpdate(FaqDTO dto) throws Exception;
	public int faqDelete(int idx) throws Exception;
}
