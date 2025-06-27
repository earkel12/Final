package com.pm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pm.reser.model.ReserDTO;
import com.pm.reser.model.UserCarDTO;

@Mapper
public interface ReserMapper {
	public void insertReser(ReserDTO booking) throws Exception;
	public List<UserCarDTO> carbyid(String userid) throws Exception;
}
