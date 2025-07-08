package com.pm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pm.booking.model.BookingDTO;
import com.pm.booking.model.BookingParkingDTO;
import com.pm.booking.model.UserCarDTO;
import com.pm.map.model.ParkingLotDTO;

@Mapper
public interface BookingMapper {
	public void insertBooking(BookingDTO booking) throws Exception;
	public List<UserCarDTO> carbyid(String userid) throws Exception;
	public void updateStatus(String userid) throws Exception;
	//0원결제상태변경 로직 추가 후 파킹메이트 결제상태변경이 안되어 쿼리문변경 후 메소드 파라미터 변경
	public void finalupdateStatus(Map<String, Object> param) throws Exception;
	public int bookingCount(int idx) throws Exception;

	public List<BookingParkingDTO> selectActiveInstadBookings();
	public void updateStatusToReserved(@Param("bookingnum") int bookingnum);
	public BookingDTO getBookingByNum(int bookingnum);
	public List<BookingParkingDTO> getBookingParkingListByMateId(String mateId);
	
	//0원이여도 결제완료상태 변경
	public int getBookingPriceById(@Param("id") String id, @Param("bookingcarnum") String bookingcarnum) throws Exception;
	public void updateBookingStatusIfPriceZero(Map<String, Object> param) throws Exception;
	//메이트이용현황관련
	public List<String> findBookingCarNumByUser(String id) throws Exception;
	public List<Map<String, Object>> findBookingInfoByCarNum(String id, String bookingcarnum) throws Exception;
	// 추가할 메서드
    public List<Map<String, Object>> findBookingInfoByCarNumWithEndtime(Map<String, Object> map) throws Exception;
	public ParkingLotDTO findParkinglotByName(String name) throws Exception;
	public List<Map<String, Object>> findMatcingMate(@Param("id")String id, @Param("car_num")String car_num) throws Exception;

	public int updateStartTimePaycheck(int bookingnum) throws Exception;
	public int updateOuttime(int bookingnum) throws Exception;
	public void addCalculatedPriceToBooking(@Param("bookingnum") int bookingnum, @Param("additionalPrice") int additionalPrice) throws Exception;
	
	public int updateIntime(int bookingnum) throws Exception;
	public Map<String, Object> findIntimeAndPrice2(int bookingnum) throws Exception;
	public int updateOuttime(Map<String, Object> param) throws Exception;

}


