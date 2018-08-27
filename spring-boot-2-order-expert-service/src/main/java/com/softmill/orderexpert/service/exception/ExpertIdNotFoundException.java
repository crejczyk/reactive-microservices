package com.softmill.orderexpert.service.exception;

public class ExpertIdNotFoundException extends RuntimeException {
    public ExpertIdNotFoundException(String message) {
        super(message);
    }

    public ExpertIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
