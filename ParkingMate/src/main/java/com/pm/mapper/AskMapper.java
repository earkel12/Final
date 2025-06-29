package com.pm.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.pm.ask.model.AskDTO;

@Mapper
public interface AskMapper {

	public int askInsert(AskDTO dto) throws Exception;
}
