package com.pm.pm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.mapper.ParkingMateMapper;
import com.pm.pm.model.MatePayCheckDTO;
import com.pm.pm.model.ParkingMateDTO;

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
    public List<MatePayCheckDTO> getMatePayCheck() throws Exception {
    	return mapper.getMatePayCheck();
    }
    @Override
    public ParkingMateDTO getParkingMate(String id) throws Exception {
    	return mapper.getParkingMate(id);
    }
}

