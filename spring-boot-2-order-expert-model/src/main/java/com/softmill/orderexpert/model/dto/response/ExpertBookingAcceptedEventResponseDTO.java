package com.softmill.orderexpert.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpertBookingAcceptedEventResponseDTO {

    private String expertBookingId;

    private String expertId;

    private Date acceptedTime;
}
