package com.softmill.orderexpert.service.service;

import java.util.Optional;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import com.softmill.orderexpert.model.converter.LocationToPointConverter;
import com.softmill.orderexpert.model.dto.request.ExpertRegisterEventDTO;
import com.softmill.orderexpert.model.dto.request.LocationDTO;
import com.softmill.orderexpert.model.enums.ExpertStatus;
import com.softmill.orderexpert.model.enums.ExpertType;
import com.softmill.orderexpert.service.exception.ExpertIdNotFoundException;
import com.softmill.orderexpert.service.model.Expert;
import com.softmill.orderexpert.service.repo.ExpertRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExpertService {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    private final ExpertRepository expertRepository;
    private final LocationToPointConverter locationToPointConverter = new LocationToPointConverter();

    public ExpertService(ReactiveRedisTemplate<String, String> reactiveRedisTemplate, ExpertRepository expertRepository) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
        this.expertRepository = expertRepository;
    }

    public Mono<Expert> register(ExpertRegisterEventDTO expertRegisterEventDTO) {
        Expert expert = new Expert(expertRegisterEventDTO.getExpertId(), expertRegisterEventDTO.getExpertType(), ExpertStatus.AVAILABLE);
        return Mono.just(expertRepository.save(expert));
    }

    public Mono<Expert> updateLocation(String expertId, LocationDTO locationDTO) {
        Optional<Expert> expertOptional = expertRepository.findById(expertId);
        if (expertOptional.isPresent()) {
            Expert expert = expertOptional.get();
            return reactiveRedisTemplate.opsForGeo().add(expert.getExpertType().toString(), locationToPointConverter.convert(locationDTO), expertId.toString()).flatMap(l -> Mono.just(expert));
        } else {
            throw getExpertIdNotFoundException(expertId);
        }
    }

    public Flux<GeoResult<RedisGeoCommands.GeoLocation<String>>> getAvailableExperts(ExpertType expertType, Double latitude, Double longitude, Double radius) {
        return reactiveRedisTemplate.opsForGeo().radius(expertType.toString(), new Circle(new Point(longitude, latitude), new Distance(radius, Metrics.KILOMETERS)));
    }

    public Mono<ExpertStatus> getExpertStatus(String expertId) {
        Optional<Expert> expertOptional = expertRepository.findById(expertId);
        if (expertOptional.isPresent()) {
            Expert expert = expertOptional.get();
            return Mono.just(expert.getExpertStatus());
        } else {
            throw getExpertIdNotFoundException(expertId);
        }

    }

    public Mono<Expert> updateExpertStatus(String expertId, ExpertStatus expertStatus) {
        Optional<Expert> expertOptional = expertRepository.findById(expertId);
        if (expertOptional.isPresent()) {
            Expert expert = expertOptional.get();
            expert.setExpertStatus(expertStatus);
            return Mono.just(expertRepository.save(expert));
        } else {
            throw getExpertIdNotFoundException(expertId);
        }
    }

    private ExpertIdNotFoundException getExpertIdNotFoundException(String expertId) {
        return new ExpertIdNotFoundException("Expert Id "+expertId+" Not Found");
    }

}
