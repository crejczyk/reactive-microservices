package com.softmill.orderexpert.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpertBookingCanceledEventDTO {

	private String expertBookingId;

	private String reason;

	private Date cancelTime = new Date();

}
