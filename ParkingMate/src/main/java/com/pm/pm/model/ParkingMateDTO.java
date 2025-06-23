package com.pm.pm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ParkingMateDTO {

	private Integer idx;
	private String license;
	private String history;
	private String picture;
	private String addr;
	private String bank;
	private Integer account;
	private String id;

}
