package com.pm.booking.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingParkingDTO {
	private int bookingnum;
    private Date bookingdate;
    private String bookingcarnum;
    private Date intime;
    private Date outtime;
    private int valet;
    private int instand;
    private int price;
    private String status;
    private int obstacle;

    private int lotIdx;
    private String lotName;
    private String lotAddr;
    private String lotType;
    private int lotPrice;

}
