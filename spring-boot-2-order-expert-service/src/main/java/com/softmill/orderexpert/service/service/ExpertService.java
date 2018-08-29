package com.softmill.orderexpert.service.service;

import org.springframework.data.geo.GeoResult;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;

import com.softmill.orderexpert.model.dto.request.ExpertRegisterEventDTO;
import com.softmill.orderexpert.model.dto.request.LocationDTO;
import com.softmill.orderexpert.model.enums.ExpertStatus;
import com.softmill.orderexpert.model.enums.ExpertType;
import com.softmill.orderexpert.service.model.Expert;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExpertService {

	Mono<Expert> register(ExpertRegisterEventDTO expertRegisterEventDTO);

	Mono<Expert> updateLocation(String expertId, LocationDTO locationDTO);

	Flux<GeoResult<GeoLocation<String>>> getAvailableExperts(ExpertType expertType, Double latitude, Double longitude,
			Double radius);

	Mono<ExpertStatus> getExpertStatus(String expertId);

	Mono<Expert> updateExpertStatus(String expertId, ExpertStatus expertStatus);

}