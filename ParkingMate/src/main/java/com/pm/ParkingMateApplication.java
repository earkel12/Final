package com.pm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pm.mapper")
public class ParkingMateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingMateApplication.class, args);
	}

}
