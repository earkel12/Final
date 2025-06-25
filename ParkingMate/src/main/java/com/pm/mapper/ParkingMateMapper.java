package com.pm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pm.pm.model.MatePayCheckDTO;
import com.pm.pm.model.ParkingMateDTO;

@Mapper
public interface ParkingMateMapper {
	public ParkingMateDTO getParkingMate(String id) throws Exception;
	public int insertParkingMate(ParkingMateDTO dto) throws Exception;
	public int updateParkingMate(ParkingMateDTO dto) throws Exception;
	public List<MatePayCheckDTO> getMatePayCheck(Map<String, Object> params) throws Exception;
	public Map<String, Object> totalPmWorklog(String mid) throws Exception;
}
