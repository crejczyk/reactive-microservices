package com.softmill.orderexpert.booking.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softmill.orderexpert.booking.service.service.ExpertBookingService;
import com.softmill.orderexpert.model.dto.request.ExpertBookedEventDTO;
import com.softmill.orderexpert.model.dto.request.ExpertBookingAcceptedEventDTO;
import com.softmill.orderexpert.model.dto.request.ExpertBookingCanceledEventDTO;
import com.softmill.orderexpert.model.dto.response.ExpertBookedEventResponseDTO;
import com.softmill.orderexpert.model.dto.response.ExpertBookingAcceptedEventResponseDTO;
import com.softmill.orderexpert.model.dto.response.ExpertBookingCanceledEventResponseDTO;
import com.softmill.orderexpert.model.dto.response.ExpertBookingResponseDTO;
import com.softmill.orderexpert.model.enums.ExpertType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/expertbookings")
@RestController
public class ExpertBookingController {

    private final ExpertBookingService expertBookingService;

	public ExpertBookingController(ExpertBookingService expertBookingService) {
		this.expertBookingService = expertBookingService;
	}

	@PostMapping
	public Mono<ExpertBookedEventResponseDTO> book(@RequestBody ExpertBookedEventDTO expertBookedEventDTO) {
		return expertBookingService.book(expertBookedEventDTO)
				.map(t -> new ExpertBookedEventResponseDTO(t.getExpertBookingId()));
	}

	@PutMapping("/cancel")
	public Mono<ExpertBookingCanceledEventResponseDTO> cancel(
			@RequestBody ExpertBookingCanceledEventDTO expertBookingCanceledEventDTO) {
		return expertBookingService.cancel(expertBookingCanceledEventDTO)
				.map(t -> new ExpertBookingCanceledEventResponseDTO(t.getExpertBookingId()));
	}

	@PutMapping("/accept")
	public Mono<ExpertBookingAcceptedEventResponseDTO> accept(
			@RequestBody ExpertBookingAcceptedEventDTO expertBookingAcceptedEventDTO) {
		return expertBookingService.accept(expertBookingAcceptedEventDTO)
				.map(t -> new ExpertBookingAcceptedEventResponseDTO(t.getExpertBookingId(), t.getExpertId(),
						t.getAcceptedTime()));
	}

	@GetMapping
	public Flux<ExpertBookingResponseDTO> getBookings(@RequestParam("type") ExpertType expertType,
			@RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude,
			@RequestParam(value = "radius", defaultValue = "1") Double radius) {
		return expertBookingService.getBookings(expertType, latitude, longitude, radius)
				.map(r -> new ExpertBookingResponseDTO(r.getContent().getName()));
	}

}
