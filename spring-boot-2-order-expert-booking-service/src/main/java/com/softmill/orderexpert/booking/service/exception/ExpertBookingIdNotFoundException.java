package com.softmill.orderexpert.booking.service.exception;

public class ExpertBookingIdNotFoundException extends RuntimeException {

    public ExpertBookingIdNotFoundException(String message) {
        super(message);
    }

    public ExpertBookingIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
