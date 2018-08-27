package com.softmill.orderexpert.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpertBookingAcceptedEventDTO {

    private String expertBookingId;

    private String expertId;

    private Date acceptedTime = new Date();

}
