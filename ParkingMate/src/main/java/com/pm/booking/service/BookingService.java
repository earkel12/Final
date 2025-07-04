package com.pm.booking.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pm.booking.model.BookingDTO;
import com.pm.booking.model.BookingParkingDTO;
import com.pm.booking.model.UserCarDTO;
import com.pm.map.model.ParkingLotDTO;

public interface BookingService {	
	public void insertBooking(BookingDTO booking) throws Exception;
	public List<UserCarDTO> carbyid(String userid) throws Exception;
	public void updateStatus(String userid) throws Exception;

	public int bookingCount(int idx) throws Exception;

	public void updateStatusToReserved(@Param("bookingnum") int bookingnum);
	public BookingDTO getBookingByNum(int bookingnum);

	
	//메이트이용현황관련
	public List<BookingParkingDTO> getActiveInstadBookings();
	public List<BookingParkingDTO> getBookingParkingListByMateId(String mateId);
	public List<Map<String, Object>> showMatebookingList(String id)throws Exception;
	public ParkingLotDTO findParkinglotByName(String name) throws Exception;
	public List<Map<String, Object>> findMatcingMate(@Param("id")String id, @Param("car_num")String car_num) throws Exception;
	public int updateInTime(int bookingnum) throws Exception;
	public int updateOuttime(int bookingnum) throws Exception;
}
