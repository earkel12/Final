package com.pm.com.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
	
	private String id;
	private int bookingnum;
	private Date writedate;
	private int rating;
	private String content;
	private String upload;
	
}
