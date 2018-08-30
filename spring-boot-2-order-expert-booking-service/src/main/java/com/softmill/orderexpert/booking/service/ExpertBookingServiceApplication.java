package com.softmill.orderexpert.booking.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.softmill.orderexpert.config.RedisConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@SpringBootApplication
@Import(RedisConfig.class)
@EnableSwagger2WebFlux
public class ExpertBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpertBookingServiceApplication.class, args);
	}

}
