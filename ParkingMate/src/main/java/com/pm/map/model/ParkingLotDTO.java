package com.pm.map.model;

import java.sql.Date;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ParkingLotDTO {

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
	private int weight;
}
