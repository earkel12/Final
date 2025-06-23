package com.pm.pm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.mapper.ParkingMateMapper;
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
}

