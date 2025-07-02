package com.pm.booking.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

	private String bookingnum;
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
	private double ulatitude;
	private double ulongitude;
	private double pmlatitude;
	private double pmlongitude;
<<<<<<< HEAD
<<<<<<< HEAD
}
=======
}	
>>>>>>> e9064c3ae755ff524c0d05e67674937599450347
=======
  
}	

>>>>>>> origin/feat/developusagestatus

