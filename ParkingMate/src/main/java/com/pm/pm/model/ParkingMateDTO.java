package com.pm.pm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingMateDTO {

	private int idx;
	private String license;
	private String history;
	private String picture;
	private String addr;
	private String bank;
	private long account;
	private String id;

}
