package com.pm.mypage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Car_TypeDTO {
	private String modelname;
	private String brandname;
	private int length;
	private int width;
	private int height;
	private int weight;
}
