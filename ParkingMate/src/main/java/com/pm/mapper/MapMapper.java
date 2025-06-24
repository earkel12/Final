package com.pm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pm.map.model.ParkingLotDTO;

@Mapper
public interface MapMapper {
	public ParkingLotDTO plInfo(String name) throws Exception;
}
