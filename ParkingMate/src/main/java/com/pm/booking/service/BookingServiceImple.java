package com.pm.booking.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

	    Timestamp intimeTimestamp = toTimestamp(findInfo.get("intime"));
	    if (intimeTimestamp == null) {
	        throw new IllegalAccessException("입차시간이 정상적으로 등록되지 않았습니다. 고객센터로 연락바랍니다.");
	    }

	    LocalDateTime intime = intimeTimestamp.toLocalDateTime();
	    long minutes = ChronoUnit.MINUTES.between(intime, LocalDateTime.now());
	    long units = (long) Math.ceil((double) minutes / 30);
	    int pricePerUnit = (int) findInfo.get("price2");
	    int additionalPrice = (int) (units * pricePerUnit);

	    int alreadyPaidPrice = (int) findInfo.get("price");
	    int totalprice = alreadyPaidPrice + additionalPrice;

	    Map<String, Object> updateMap = new HashMap<>();
	    updateMap.put("bookingnum", bookingnum);
	    updateMap.put("price", totalprice); // DB에는 누적금액 저장

	    int updateCount = mapper.updateOuttime(updateMap);
	    System.out.println("updateCount: " + updateCount);
	    if (updateCount == 0) {
	        throw new IllegalStateException("출차 처리 실패: bookingnum 불일치 또는 업데이트 실패.");
	    }

	    Map<String, Object> result = new HashMap<>();
	    result.put("success", true);
	    result.put("bookingnum", bookingnum);
	    result.put("price", additionalPrice); // 화면에는 추가 결제금액만 표시
	    result.put("minutes", minutes);
	    result.put("units", units);
	    result.put("outtime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

	    return result;
	}


	private Timestamp toTimestamp(Object intimeObj) {
	    if (intimeObj instanceof Timestamp) {
	        return (Timestamp) intimeObj;
	    } else if (intimeObj instanceof String) {
	        try {
	            return Timestamp.valueOf((String) intimeObj);
	        } catch (IllegalArgumentException e) {
	            try {
	                LocalDateTime ldt = LocalDateTime.parse((String) intimeObj, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	                return Timestamp.valueOf(ldt);
	            } catch (Exception ex) {
	                throw new IllegalStateException("intime 문자열을 Timestamp로 변환 실패: " + intimeObj, ex);
	            }
	        }
	    } else if (intimeObj != null) {
	        throw new IllegalStateException("Unknown type for intime: " + intimeObj.getClass());
	    }
	    return null;
	}
	
	@Override
	public void addCalculatedPriceToBooking(int bookingnum, int additionalPrice) throws Exception {
		mapper.addCalculatedPriceToBooking(bookingnum, additionalPrice);
		
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
