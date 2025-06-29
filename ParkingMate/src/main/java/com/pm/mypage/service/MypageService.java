package com.pm.mypage.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pm.mypage.model.Car_TypeDTO;
import com.pm.mypage.model.User_CarsDTO;

public interface MypageService {

	public int carRegisterForm(User_CarsDTO dto) throws Exception;

	public List<Car_TypeDTO> findCarModelName(String fkey, String fvalue) throws Exception; 
	
	public List<User_CarsDTO> checkMyCarList(String id) throws Exception;
	
	public User_CarsDTO showMyCarInfo(String car_num) throws Exception;  
	public int updateMyCarInfo(Map<String, Object> map) throws Exception;

	public int myCarDelete(String car_num) throws Exception;
}
