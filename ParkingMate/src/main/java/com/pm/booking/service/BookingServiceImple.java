package com.pm.booking.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
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
	//0원결제상태변경 로직 추가 후 파킹메이트 결제상태변경이 안되어 쿼리문변경 후 메소드 파라미터 변경
	//그에 따라 오버라이딩 내용 변경
	@Override
	public void finalupdateStatus(String id, String bookingcarnum) throws Exception {
	    Map<String, Object> param = new HashMap<>();
	    param.put("id", id);
	    param.put("bookingcarnum", bookingcarnum);
	    mapper.finalupdateStatus(param);
	}
	
	@Override
	public int bookingCount(int idx) throws Exception {
		return mapper.bookingCount(idx);
	}

	@Override
	public void updateStatusToReserved(int  bookingnum) {
		 mapper.updateStatusToReserved(bookingnum);
	}
	@Override
	public BookingDTO getBookingByNum(int bookingnum) {
		return mapper.getBookingByNum(bookingnum);
	}
	
	//0원이여도 결제상태완료로 변경
	@Override
	public int getBookingPriceById(String id, String bookingcarnum) throws Exception {
		return mapper.getBookingPriceById(id, bookingcarnum);
	}
	@Override
	public void updateBookingStatusIfPriceZero(String id, String bookingcarnum) throws Exception {
		Map<String, Object> param = new HashMap<>();
	    param.put("id", id);
	    param.put("bookingcarnum", bookingcarnum);
	    mapper.updateBookingStatusIfPriceZero(param);
	}
	
	//메이트이용현황관련	
	@Override
	public List<String> findBookingCarNumByUser(String id) throws Exception {
		return mapper.findBookingCarNumByUser(id);
	}
	
	@Override
	public List<Map<String, Object>> findBookingInfoByCarNum(String id, String bookingcarnum) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
	    paramMap.put("id", id);
	    paramMap.put("bookingcarnum", bookingcarnum);
	    return mapper.findBookingInfoByCarNumWithEndtime(paramMap);
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
	public Map<String, Object> updateIntime(int bookingnum) throws Exception {
		
		System.out.println("=== updateIntime Controller 진입 ===");
		
		int updateCount1 = mapper.updateIntime(bookingnum);
		System.out.println("updateCount1:"+updateCount1);
		
		if(updateCount1==0) {
			throw new IllegalAccessException("입차처리실패:오류발생 고객센터에 문의바랍니다.");
		}
		//클라이언트로 반환할 데이터 구성
		Map<String, Object> result = new HashMap<>();
		result.put("bookingnum", bookingnum);
		result.put("success", true);
		
		return result;
	}
	
	@Override
	public Map<String, Object> updateOuttime(int bookingnum) throws Exception {
		
		System.out.println("=== updateOuttime Controller 진입 ===");
		
		Map<String, Object> findInfo = mapper.findIntimeAndPrice2(bookingnum);
		  if (findInfo == null) {
		        throw new IllegalStateException("해당 bookingnum에 대한 데이터가 없습니다.");
		  }
		
		Timestamp intimeTimestamp = (Timestamp)findInfo.get("intime");
		int price2 = (int)findInfo.get("price2");
		
		if(intimeTimestamp==null) {
			throw new IllegalAccessException("입차시간이 정상적으로 등록되지 않았습니다. 고객센터로 연락바랍니다.");
		}
		
		LocalDateTime intime = intimeTimestamp.toLocalDateTime();
		//경과시간계산
		long minutes = ChronoUnit.MINUTES.between(intime, LocalDateTime.now());
		//30분단위 올림처리
		long units=(long)Math.ceil((double)minutes/30);
		int price=(int)(units*price2);
		
		// 이미 결제된 금액 가져오기
		int alreadyPaidPrice = (int) findInfo.get("price");

		// ★ DB에 저장할 최종 누적 결제 금액
	    int totalprice = alreadyPaidPrice + price;
		
		//DB 업데이트 수행 (outtime = now, price = 계산된 금액)
	    Map<String, Object> updateMap = new HashMap<>();
	    updateMap.put("bookingnum", bookingnum);
	    updateMap.put("price", totalprice);
	    
	    int updateCount2 = mapper.updateOuttime(updateMap);
	    System.out.println("updateCount2: " + updateCount2);

	    if (updateCount2 == 0) {
	        throw new IllegalStateException("출차 처리 실패: bookingnum 불일치 또는 업데이트 실패.");
	    }
	    //클라이언트 반환 데이터 구성
	    Map<String, Object> result = new HashMap<>();
	    result.put("bookingnum", bookingnum);
	    result.put("price", price);
	    result.put("minutes", minutes);
	    result.put("units", units);

	    return result;
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
