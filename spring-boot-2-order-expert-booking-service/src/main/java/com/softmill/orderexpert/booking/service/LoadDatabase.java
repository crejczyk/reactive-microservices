package com.softmill.orderexpert.booking.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import com.softmill.orderexpert.booking.service.service.ExpertBookingService;
import com.softmill.orderexpert.model.dto.request.ExpertBookedEventDTO;
import com.softmill.orderexpert.model.enums.ExpertType;
import com.softmill.orderexpert.model.util.LocationGenerator;

@Configuration
public class LoadDatabase {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoadDatabase.class);

	private final ExpertBookingService expertBookingService;

	public LoadDatabase(ExpertBookingService tweetRepository) {
		this.expertBookingService = tweetRepository;
	}

	@EventListener(value = ContextRefreshedEvent.class)
	void init() {
		initBooking();
	}

	private void initBooking() {
		log.info("Start data initialization ...");
		for (int i = 0; i < 3; i++) {
			expertBookingService.book(new ExpertBookedEventDTO(UUID.randomUUID().toString(),
					LocationGenerator.getLocation(79.865072, 6.927610, 3000),
					LocationGenerator.getLocation(79.865072, 6.927610, 3000), new Date(), 1l, ExpertType.CARPENTER))
					.subscribe();
		}
	}
}