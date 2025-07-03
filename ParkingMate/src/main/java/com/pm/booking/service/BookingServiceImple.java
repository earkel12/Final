package com.pm.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import com.pm.booking.model.BookingDTO;
import com.pm.booking.model.UserCarDTO;
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
	public int bookingCount(int idx) throws Exception {
		return mapper.bookingCount(idx);
	}
}
