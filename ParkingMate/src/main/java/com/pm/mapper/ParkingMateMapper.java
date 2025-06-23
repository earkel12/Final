package com.pm.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.pm.pm.model.ParkingMateDTO;

@Mapper
public interface ParkingMateMapper {
	public int insertParkingMate(ParkingMateDTO dto) throws Exception;
}
