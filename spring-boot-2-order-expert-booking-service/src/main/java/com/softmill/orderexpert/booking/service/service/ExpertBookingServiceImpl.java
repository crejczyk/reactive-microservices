package com.softmill.orderexpert.booking.service.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softmill.orderexpert.booking.service.exception.ExpertBookingIdNotFoundException;
import com.softmill.orderexpert.booking.service.model.ExpertBooking;
import com.softmill.orderexpert.booking.service.repo.ExpertBookingRepository;
import com.softmill.orderexpert.config.RedisConfig;
import com.softmill.orderexpert.model.converter.LocationToPointConverter;
import com.softmill.orderexpert.model.dto.request.ExpertBookedEventDTO;
import com.softmill.orderexpert.model.dto.request.ExpertBookingAcceptedEventDTO;
import com.softmill.orderexpert.model.dto.request.ExpertBookingCanceledEventDTO;
import com.softmill.orderexpert.model.enums.ExpertBookingStatus;
import com.softmill.orderexpert.model.enums.ExpertType;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExpertBookingServiceImpl implements ExpertBookingService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExpertBookingService.class);

    private final RedisTemplate<String, String> redisTemplate;
    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    private final ExpertBookingRepository expertBookingRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LocationToPointConverter locationToPointConverter = new LocationToPointConverter();

	public ExpertBookingServiceImpl(RedisTemplate<String, String> redisTemplate,
			ReactiveRedisTemplate<String, String> reactiveRedisTemplate,
			ExpertBookingRepository expertBookingRepository) {
		this.redisTemplate = redisTemplate;
		this.reactiveRedisTemplate = reactiveRedisTemplate;
		this.expertBookingRepository = expertBookingRepository;
	}

	@Override
	public Mono<ExpertBooking> book(ExpertBookedEventDTO expertBookedEventDTO) {
		ExpertBooking expertBooking = new ExpertBooking();
		expertBooking.setLocation(locationToPointConverter.convert(expertBookedEventDTO.getLocation()));
		expertBooking.setBookedTime(expertBookedEventDTO.getBookedTime());
		expertBooking.setCustomerId(expertBookedEventDTO.getCustomerId());
		expertBooking.setBookingStatus(ExpertBookingStatus.ACTIVE);
		ExpertBooking savedExpertBooking = expertBookingRepository.save(expertBooking);

		return reactiveRedisTemplate
				.opsForGeo().add(getExpertTypeBookings(expertBookedEventDTO.getExpertType()),
						expertBooking.getLocation(), expertBooking.getExpertBookingId())
				.flatMap(l -> Mono.just(savedExpertBooking));
	}

	@Override
	public Mono<ExpertBooking> cancel(ExpertBookingCanceledEventDTO canceledEventDTO) {
		String expertBookingId = canceledEventDTO.getExpertBookingId();
		Optional<ExpertBooking> expertBookingOptional = expertBookingRepository.findById(expertBookingId);
		return expertBookingOptional.map(expertBooking -> {
			expertBooking.setBookingStatus(ExpertBookingStatus.CANCELLED);
			expertBooking.setReasonToCancel(canceledEventDTO.getReason());
			expertBooking.setCancelTime(canceledEventDTO.getCancelTime());
			return Mono.just(expertBookingRepository.save(expertBooking));
		}).orElseThrow(() -> getExpertBookingIdNotFoundException(expertBookingId));
	}

	@Override
	public Mono<ExpertBooking> accept(ExpertBookingAcceptedEventDTO acceptedEventDTO) {
		String expertBookingId = acceptedEventDTO.getExpertBookingId();
		Optional<ExpertBooking> expertBookingOptional = expertBookingRepository.findById(expertBookingId);
		return expertBookingOptional.map(expertBooking -> {
			expertBooking.setExpertId(acceptedEventDTO.getExpertId());
			expertBooking.setAcceptedTime(acceptedEventDTO.getAcceptedTime());
			return Mono.just(expertBookingRepository.save(expertBooking)).doOnSuccess(t -> {
				try {
					redisTemplate.convertAndSend(RedisConfig.ACCEPTED_EVENT_CHANNEL,
							objectMapper.writeValueAsString(acceptedEventDTO));
				} catch (JsonProcessingException e) {
					LOGGER.error("Error while sending message to Channel {}", RedisConfig.ACCEPTED_EVENT_CHANNEL, e);
				}
			});
		}).orElseThrow(() -> getExpertBookingIdNotFoundException(expertBookingId));
	}

	@Override
	public Flux<GeoResult<RedisGeoCommands.GeoLocation<String>>> getBookings(ExpertType expertType, Double latitude,
			Double longitude, Double radius) {
		return reactiveRedisTemplate.opsForGeo().radius(getExpertTypeBookings(expertType),
				new Circle(new Point(longitude, latitude), new Distance(radius, Metrics.KILOMETERS)));
	}

	@Override
	public Mono<ExpertBooking> updateBookingStatus(String expertBookingId, ExpertBookingStatus expertBookingStatus) {
		Optional<ExpertBooking> expertBookingOptional = expertBookingRepository.findById(expertBookingId);
		return expertBookingOptional.map(expertBooking -> {
			expertBooking.setBookingStatus(expertBookingStatus);
			return Mono.just(expertBookingRepository.save(expertBooking));
		}).orElseThrow(() -> getExpertBookingIdNotFoundException(expertBookingId));
	}

	@Override
	public Mono<Void> deleteAll() {
		expertBookingRepository.deleteAll();
		return Mono.empty();
	}

	private ExpertBookingIdNotFoundException getExpertBookingIdNotFoundException(String expertBookingId) {
		return new ExpertBookingIdNotFoundException(String.format("Booking Id %s Not Found", expertBookingId));
	}

	private String getExpertTypeBookings(ExpertType expertType) {
		return String.format("%s-Bookings", expertType.toString());
	}

}
