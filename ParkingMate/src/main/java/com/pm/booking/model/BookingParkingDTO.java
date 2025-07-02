package com.pm.booking.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingParkingDTO {
	
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

    private Double latitude;
    private Double longitude;
    private String name;
    private String addr;
    
    private int lotIdx;
    private String lotName;
    private String lotAddr;
    private String lotType;
    private int lotPrice;

}
