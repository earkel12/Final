package com.pm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pm.pm.model.MatePayCheckDTO;
import com.pm.pm.model.ParkingMateDTO;

@Mapper
public interface ParkingMateMapper {
	public ParkingMateDTO getParkingMate(String id) throws Exception;
	public int insertParkingMate(ParkingMateDTO dto) throws Exception;
	public List<MatePayCheckDTO> getMatePayCheck() throws Exception;
}
