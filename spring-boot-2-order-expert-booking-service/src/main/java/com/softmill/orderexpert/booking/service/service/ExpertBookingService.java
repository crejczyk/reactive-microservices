package com.softmill.orderexpert.booking.service.service;

import org.springframework.data.geo.GeoResult;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;

import com.softmill.orderexpert.booking.service.model.ExpertBooking;
import com.softmill.orderexpert.model.dto.request.ExpertBookedEventDTO;
import com.softmill.orderexpert.model.dto.request.ExpertBookingAcceptedEventDTO;
import com.softmill.orderexpert.model.dto.request.ExpertBookingCanceledEventDTO;
import com.softmill.orderexpert.model.enums.ExpertBookingStatus;
import com.softmill.orderexpert.model.enums.ExpertType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExpertBookingService {

	Mono<ExpertBooking> book(ExpertBookedEventDTO expertBookedEventDTO);

	Mono<ExpertBooking> cancel(ExpertBookingCanceledEventDTO canceledEventDTO);

	Mono<ExpertBooking> accept(ExpertBookingAcceptedEventDTO acceptedEventDTO);

	Flux<GeoResult<GeoLocation<String>>> getBookings(ExpertType expertType, Double latitude, Double longitude,
			Double radius);

	Mono<ExpertBooking> updateBookingStatus(String expertBookingId, ExpertBookingStatus expertBookingStatus);

	Mono<Void> deleteAll();

   

}
