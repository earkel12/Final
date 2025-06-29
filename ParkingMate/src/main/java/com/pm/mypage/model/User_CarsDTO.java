package com.pm.mypage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class User_CarsDTO {
	private String car_num;
	private int model_year;
	private String gas_type;
	private String color;
	private String gear;
	private String id;
	private String modelname;
	
}
