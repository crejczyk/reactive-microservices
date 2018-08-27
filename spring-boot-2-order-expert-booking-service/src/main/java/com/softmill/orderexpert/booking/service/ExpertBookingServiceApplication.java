package com.softmill.orderexpert.booking.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.softmill.orderexpert.booking.service.service.ExpertBookingService;
import com.softmill.orderexpert.config.RedisConfig;
import com.softmill.orderexpert.model.dto.request.ExpertBookedEventDTO;
import com.softmill.orderexpert.model.enums.ExpertType;
import com.softmill.orderexpert.model.util.LocationGenerator;

@SpringBootApplication
@Import(RedisConfig.class)
public class ExpertBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpertBookingServiceApplication.class, args);
	}

}
