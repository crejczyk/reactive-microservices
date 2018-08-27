package com.softmill.orderexpert.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

import com.softmill.orderexpert.model.enums.ExpertType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpertBookedEventDTO {

	private String expertBookingId = UUID.randomUUID().toString();

	private LocationDTO start;

	private LocationDTO end;

	private Date bookedTime = new Date();

	private Long customerId;

	private ExpertType expertType;

}
