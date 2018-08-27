package com.softmill.orderexpert.booking.service.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisHash;

import com.softmill.orderexpert.model.enums.ExpertBookingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RedisHash("ExpertBooking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpertBooking {
    @Id
    private String expertBookingId;

    private Point start;

    private Date startTime;

    private Point end;

    private Date endTime;

    private Date bookedTime;

    private Date acceptedTime;

    private Long customerId;

    private ExpertBookingStatus bookingStatus;

    private String reasonToCancel;

    private Date cancelTime;

    private String expertId;
}
