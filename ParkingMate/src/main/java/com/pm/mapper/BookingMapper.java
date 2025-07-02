package com.pm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pm.booking.model.BookingDTO;
import com.pm.booking.model.BookingParkingDTO;
import com.pm.booking.model.UserCarDTO;

@Mapper
public interface BookingMapper {
	public void insertBooking(BookingDTO booking) throws Exception;
	public List<UserCarDTO> carbyid(String userid) throws Exception;
	public void updateStatus(String userid) throws Exception;
	public List<BookingParkingDTO> selectActiveInstadBookings();
	public void updateStatusToReserved(@Param("bookingnum") int bookingnum);
	public BookingDTO getBookingByNum(int bookingnum);
}
