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
	//0원결제상태변경 로직 추가 후 파킹메이트 결제상태변경이 안되어 쿼리문변경 후 메소드 파라미터 변경
	public void finalupdateStatus(String id, String bookingcarnum) throws Exception;
	public int bookingCount(int idx) throws Exception;

	public void updateStatusToReserved(@Param("bookingnum") int bookingnum);
	public BookingDTO getBookingByNum(int bookingnum);

	public List<BookingParkingDTO> getActiveInstadBookings();
	public List<BookingParkingDTO> getBookingParkingListByMateId(String mateId);
	
	//0원이여도 결제상태완료로 변경
	public int getBookingPriceById(String id, String bookingcarnum) throws Exception;
	public void updateBookingStatusIfPriceZero(String id, String bookingcarnum) throws Exception;
	//메이트이용현황관련
	public List<String> findBookingCarNumByUser(String id) throws Exception;
	public List<Map<String, Object>> findBookingInfoByCarNum(String id, String bookingcarnum) throws Exception;

	public ParkingLotDTO findParkinglotByName(String name) throws Exception;
	public List<Map<String, Object>> findMatcingMate(@Param("id")String id, @Param("car_num")String car_num) throws Exception;

	public Map<String, Object> updateIntime(int bookingnum) throws Exception;
	public Map<String, Object> updateOuttime(int bookingnum) throws Exception;
}

