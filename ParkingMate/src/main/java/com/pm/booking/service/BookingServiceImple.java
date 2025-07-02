package com.pm.booking.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import com.pm.booking.model.BookingDTO;
import com.pm.booking.model.BookingParkingDTO;
import com.pm.booking.model.BookingDTO;
import com.pm.booking.model.UserCarDTO;
import com.pm.map.model.ParkingLotDTO;
import com.pm.mapper.BookingMapper;


@Service
public class BookingServiceImple implements BookingService {
	
	@Autowired
	private BookingMapper mapper;
	
	@Override
	public void insertBooking(BookingDTO booking) throws Exception {
		mapper.insertBooking(booking);
	}
	
	@Override
	public List<UserCarDTO> carbyid(String userid) throws Exception {
		
		return mapper.carbyid(userid);
	}
	
	@Override
	public void updateStatus(String userid) throws Exception {
		mapper.updateStatus(userid);
	}
	@Override
	public void updateStatusToReserved(int bookingnum) {
		 mapper.updateStatusToReserved(bookingnum);
	}
	@Override
	public BookingDTO getBookingByNum(int bookingnum) {
		return mapper.getBookingByNum(bookingnum);
	}
	
	
	
	//메이트이용현황관련
	@Override
	public List<Map<String, Object>> showMatebookingList(String id) throws Exception {
		List<Map<String, Object>> mateBookingList = mapper.showMatebookingList(id);
		System.out.println("메이트 예약 리스트: " + mateBookingList);
		
		return mateBookingList;
	}
	
	@Override
	public ParkingLotDTO findParkinglotByName(String name) throws Exception {
		ParkingLotDTO dto = mapper.findParkinglotByName(name);
		return dto;
	}
	
	@Override
	public List<Map<String, Object>> findMatcingMate(@Param("id")String id, @Param("car_num")String car_num) throws Exception {
		List<Map<String, Object>> findMate = mapper.findMatcingMate(id, car_num);
		return findMate;
	}
	
	@Override
	public int updateOuttime(int bookingnum) throws Exception {
		return mapper.updateOuttime(bookingnum);
	}
	
	@Override
	public List<BookingParkingDTO> getActiveInstadBookings() {
		return mapper.selectActiveInstadBookings();
	}
	@Override
	public List<BookingParkingDTO> getBookingParkingListByMateId(String mateId) {
		return mapper.getBookingParkingListByMateId(mateId);
	}
}
