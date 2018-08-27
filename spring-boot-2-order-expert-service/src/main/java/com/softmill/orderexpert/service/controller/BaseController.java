package com.softmill.orderexpert.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.softmill.orderexpert.model.dto.response.ErrorDTO;
import com.softmill.orderexpert.service.exception.ExpertIdNotFoundException;

@ControllerAdvice
public class BaseController {

    @ExceptionHandler(ExpertIdNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleExpertIdNotFoundException(ExpertIdNotFoundException e) {
        return new ResponseEntity<ErrorDTO>(new ErrorDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
}
