package com.pm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pm.booking.model.BookingDTO;
import com.pm.pm.model.MatePayCheckDTO;
import com.pm.pm.model.ParkingMateDTO;

@Mapper
public interface ParkingMateMapper {
	public ParkingMateDTO getParkingMate(String id) throws Exception;
	public int insertParkingMate(ParkingMateDTO dto) throws Exception;
	public int updateParkingMate(ParkingMateDTO dto) throws Exception;
	public List<MatePayCheckDTO> getMatePayCheck(Map<String, Object> params) throws Exception;
	public Map<String, Object> totalPmWorklog(@Param("mid") String mid) throws Exception;
	public int insertMatePayCheck(MatePayCheckDTO dto);
	public List<MatePayCheckDTO> selectMateUsageList(String mid);
	public int getSettlementWaitingCount(String mid);
	public int updateBookingWithPmLocation(int bookingnum, Double pmlatitude, Double pmlongitude, String mateId);
	public int updateEndtimeAndStatus(String car_num) throws Exception;
	
	public String parkingmateById(String userid) throws Exception;
}
