package com.pm.mypage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.pm.mapper.MypageMapper;
import com.pm.mypage.model.Car_TypeDTO;
import com.pm.mypage.model.User_CarsDTO;

@Service
public class MypageServiceImple implements MypageService {

	@Autowired
	private MypageMapper mapper;
	
	@Override
	public int carRegisterForm(User_CarsDTO dto) throws Exception {
		
		int exists = mapper.checkCarNumExisets(dto.getCar_num());
		int limitAddNum = mapper.limitAddCar(dto.getId());
		
		if(exists>0) {
			throw new IllegalAccessException("이미 등록된 차량번호입니다.");
		} else if(limitAddNum>3) {
			throw new IllegalAccessException("등록 가능한 횟수를 넘었습니다. 등록된 차량 수를 확인해주세요.");
		}
		
		int count = mapper.carRegisterForm(dto);
		return count;
	}
	
	@Override
	public List<Car_TypeDTO> findCarModelName(String fkey, String fvalue) throws Exception {
		
		Map map = new HashMap();
		map.put("fkey", fkey);
		map.put("fvalue", fvalue);
		List<Car_TypeDTO> list = mapper.findCarModelName(map);
		
		return list;
	}
	
	@Override
	public List<User_CarsDTO> checkMyCarList(String id) throws Exception {
		List<User_CarsDTO> carList = mapper.checkMyCarList(id);
		return carList;
	}
	
	@Override
	public User_CarsDTO showMyCarInfo(String car_num) throws Exception {
		User_CarsDTO dto = mapper.showMyCarInfo(car_num);
		return dto;
	}
	
	@Override
	public int updateMyCarInfo(Map<String, Object> map) throws Exception {
		int exists = mapper.checkCarNumExistsExceptOriginal(map);
		
		if(exists>0) {
			throw new IllegalAccessException("이미 등록된 차량번호입니다.");
		}
		
		int count = mapper.updateMyCarInfo(map);
		return count;
	}
	
	@Override
	public int myCarDelete(String car_num) throws Exception {
		
		int count = mapper.myCarDelete(car_num);
		return count;
	}


	
}
