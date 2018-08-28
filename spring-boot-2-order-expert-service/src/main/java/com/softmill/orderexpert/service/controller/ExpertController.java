package com.softmill.orderexpert.service.controller;

import org.springframework.data.geo.GeoResult;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softmill.orderexpert.model.dto.request.ExpertRegisterEventDTO;
import com.softmill.orderexpert.model.dto.request.LocationDTO;
import com.softmill.orderexpert.model.dto.response.ExpertAvailableResponseDTO;
import com.softmill.orderexpert.model.dto.response.ExpertLocationUpdatedEventResponseDTO;
import com.softmill.orderexpert.model.dto.response.ExpertRegisterEventResponseDTO;
import com.softmill.orderexpert.model.dto.response.ExpertStatusDTO;
import com.softmill.orderexpert.model.enums.ExpertStatus;
import com.softmill.orderexpert.model.enums.ExpertType;
import com.softmill.orderexpert.service.service.ExpertService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/experts")
@RestController
public class ExpertController {

	private final ExpertService expertService;

	public ExpertController(ExpertService expertService) {
		this.expertService = expertService;
	}

	@GetMapping
	public Flux<ExpertAvailableResponseDTO> getAvailableExperts(@RequestParam("type") ExpertType expertType,
			@RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude,
			@RequestParam(value = "radius", defaultValue = "1") Double radius) {
		Flux<GeoResult<RedisGeoCommands.GeoLocation<String>>> availableExpertsFlux = expertService
				.getAvailableExperts(expertType, latitude, longitude, radius);
		return availableExpertsFlux.map(r -> new ExpertAvailableResponseDTO(r.getContent().getName()));
	}

	@GetMapping("/{expertId}/status")
	public Mono<ExpertStatusDTO> getExpertStatus(@PathVariable("expertId") String expertId) {
		return expertService.getExpertStatus(expertId).map(s -> new ExpertStatusDTO(expertId, s));
	}

	@PutMapping("/{expertId}/status")
	public Mono<ExpertStatusDTO> updateExpertStatus(@PathVariable("expertId") String expertId,
			@RequestParam("status") ExpertStatus expertStatus) {
		return expertService.updateExpertStatus(expertId, expertStatus)
				.map(t -> new ExpertStatusDTO(t.getExpertId(), t.getExpertStatus()));
	}

	@PutMapping("/{expertId}/location")
	public Mono<ExpertLocationUpdatedEventResponseDTO> updateLocation(@PathVariable("expertId") String expertId,
			@RequestBody LocationDTO locationDTO) {
		return expertService.updateLocation(expertId, locationDTO)
				.map(t -> new ExpertLocationUpdatedEventResponseDTO(expertId));
	}

	@PostMapping
	public Mono<ExpertRegisterEventResponseDTO> register(@RequestBody ExpertRegisterEventDTO expertRegisterEventDTO) {
		return expertService.register(expertRegisterEventDTO)
				.map(t -> new ExpertRegisterEventResponseDTO(t.getExpertId()));
	}

}
