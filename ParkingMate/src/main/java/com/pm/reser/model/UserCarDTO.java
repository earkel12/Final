package com.pm.reser.model;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCarDTO {

	private String car_num;
	private int model_year;
	private String gas_type;
	private String color;
	private String gear;
	private String id;
	private String modelname;

}
