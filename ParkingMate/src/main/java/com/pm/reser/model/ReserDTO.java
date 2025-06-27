package com.pm.reser.model;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserDTO {

	private String bookingnum;
	private LocalDateTime bookingdate;
	private String bookingcarnum;
	private LocalDateTime intime;
	private LocalDateTime outime;
	private int valet;
	private int instand;
	private int price;
	private String status;
	private int obstacle;
	private int idx;
	private String id;
}

