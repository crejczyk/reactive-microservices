package com.softmill.orderexpert.booking.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.softmill.orderexpert.booking.service.exception.ExpertBookingIdNotFoundException;
import com.softmill.orderexpert.model.dto.response.ErrorDTO;

@ControllerAdvice
public class BaseController {

    @ExceptionHandler(ExpertBookingIdNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleExpertBookingIdNotFoundException(ExpertBookingIdNotFoundException e) {
        return new ResponseEntity<ErrorDTO>(new ErrorDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
}
