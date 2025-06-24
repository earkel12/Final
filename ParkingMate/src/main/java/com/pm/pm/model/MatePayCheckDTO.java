package com.pm.pm.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatePayCheckDTO {
	
	private int idx;
	private String id;
	private Date starttime;
	private Date endtime;
	private String status;
	private int price;
	private String mid;
	private String car_num;

}
