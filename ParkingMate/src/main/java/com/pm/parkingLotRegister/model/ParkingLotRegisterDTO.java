package com.pm.parkingLotRegister.model;



import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotRegisterDTO {

	private int idx;
	private String name;
	private String addr;
	private String type;
	private int price;
	private int price2;
	private Date time;
	private int valet;
	private int maxnum;
	private int obstacle;
	private int maxheight;
	private int maxwidth;
	private int maxweight;
	private double latitude;
	private double longitude;
}
