package com.pm.pm.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pm.pm.model.MatePayCheckDTO;
import com.pm.pm.model.ParkingMateDTO;

public interface ParkingMateService {
	public ParkingMateDTO getParkingMate(String id) throws Exception;
	public int insertParkingMate(ParkingMateDTO dto) throws Exception;
	public int updateParkingMate(ParkingMateDTO dto) throws Exception;
	public List<MatePayCheckDTO> getMatePayCheck(String userid, String startDate, String endDate) throws Exception;
	public Map<String, Object> getTotalPmWorklog(String mid) throws Exception;
	public int insertMatePayCheck(MatePayCheckDTO dto);
	public List<MatePayCheckDTO> getMateUsageList(String mid);
	public int getSettlementWaitingCount(String mid);
}
