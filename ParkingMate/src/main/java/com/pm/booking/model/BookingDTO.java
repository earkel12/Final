package com.pm.booking.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

	private int bookingnum;
	private LocalDateTime bookingdate;
	private String bookingcarnum;
	private LocalDateTime intime;
	private LocalDateTime outtime;
	private int valet;
	private int instand;
	private int price;
	private String status;
	private int obstacle;
	private int idx;
	private String id;
	private Double ulatitude;
	private Double ulongitude;
	private Double pmlatitude;
	private Double pmlongitude;

}
