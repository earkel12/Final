package com.pm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pm.mypage.model.Car_TypeDTO;
import com.pm.mypage.model.User_CarsDTO;

@Mapper
public interface MypageMapper {
	
	public int carRegisterForm(User_CarsDTO dto) throws Exception;
	public int limitAddCar(String id);
	
	public List<Car_TypeDTO> findCarModelName(Map map) throws Exception; 
	public int checkCarNumExisets(String car_num);
	
	public List<User_CarsDTO> checkMyCarList(String id) throws Exception;
	
	public User_CarsDTO showMyCarInfo(String car_num) throws Exception;
	public int checkCarNumExistsExceptOriginal(Map<String, Object> map);
	public int updateMyCarInfo(Map<String, Object> map) throws Exception;
	
	public int myCarDelete(String car_num) throws Exception;
}
