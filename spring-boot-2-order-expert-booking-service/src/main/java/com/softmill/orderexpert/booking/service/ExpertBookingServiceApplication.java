package com.softmill.orderexpert.booking.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.softmill.orderexpert.config.RedisConfig;

@SpringBootApplication
@Import(RedisConfig.class)
public class ExpertBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpertBookingServiceApplication.class, args);
	}

}
