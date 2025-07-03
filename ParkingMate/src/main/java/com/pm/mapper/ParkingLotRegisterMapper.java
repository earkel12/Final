package com.pm.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.pm.parkingLotRegister.model.ParkingLotRegisterDTO;

@Mapper
public interface ParkingLotRegisterMapper {
	
	public int parkingLotInsert(ParkingLotRegisterDTO dto) throws Exception;
	public int parkingLotDelete(String name) throws Exception;
	public int parkingLotUpdate(ParkingLotRegisterDTO dto) throws Exception;
	public int checkName(String name) throws Exception;
}
