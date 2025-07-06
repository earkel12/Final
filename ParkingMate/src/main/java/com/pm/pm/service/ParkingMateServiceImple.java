package com.pm.pm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.booking.model.BookingDTO;
import com.pm.pm.model.MatePayCheckDTO;
import com.pm.pm.model.ParkingMateDTO;
import com.pm.mapper.BookingMapper;
import com.pm.mapper.ParkingMateMapper;


@Service
public class ParkingMateServiceImple implements ParkingMateService {

    @Autowired
    private ParkingMateMapper mapper;

    @Override
    public int insertParkingMate(ParkingMateDTO dto) throws Exception{
        int count = mapper.insertParkingMate(dto);
        return count;
    }
    
    @Override
    public int updateParkingMate(ParkingMateDTO dto) throws Exception {
    	return mapper.updateParkingMate(dto);
    }
    
    @Override
    public List<MatePayCheckDTO> getMatePayCheck(String userid, String startDate, String endDate) throws Exception {
    	Map<String, Object> params = new HashMap<>();
        params.put("mid", userid);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        return mapper.getMatePayCheck(params);
    }
    @Override
    public ParkingMateDTO getParkingMate(String id) throws Exception {
    	return mapper.getParkingMate(id);
    }
    @Override
    public Map<String, Object> getTotalPmWorklog(String mid) throws Exception {
    	 return mapper.totalPmWorklog(mid);
    }
    @Override
    public int insertMatePayCheck(MatePayCheckDTO dto) {
    	return mapper.insertMatePayCheck(dto);
    }
    @Override
    public List<MatePayCheckDTO> getMateUsageList(String mid) {
    	return mapper.selectMateUsageList(mid);
    }
    @Override
    public int getSettlementWaitingCount(String mid) {
    	return mapper.getSettlementWaitingCount(mid);
    }
    @Override
    public boolean acceptBooking(BookingDTO booking, String mateId) {
        
        int updatedRows = mapper.updateBookingWithPmLocation(
            booking.getBookingnum(),
            booking.getPmlatitude(),
            booking.getPmlongitude(),
            mateId
        );

        return updatedRows > 0;
    }
    @Override
    public boolean settleMatePaycheck(String car_num) throws Exception {
        int updated = mapper.updateEndtimeAndStatus(car_num);
        return updated > 0;
    }
}

