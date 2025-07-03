package com.pm.booking.service;

import java.util.List;

import com.pm.booking.model.BookingDTO;
import com.pm.booking.model.UserCarDTO;

public interface BookingService {	
	public void insertBooking(BookingDTO booking) throws Exception;
	public List<UserCarDTO> carbyid(String userid) throws Exception;
	public void updateStatus(String userid) throws Exception;
	public int bookingCount(int idx) throws Exception;
}
