package com.pm.pm.model;

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
public class ParkingMateDTO {
	private int idx;
	private String license;
	private String history;
	private String picture;
	private String addr;
	private String bank;
	private int account;
	private String id;
}
