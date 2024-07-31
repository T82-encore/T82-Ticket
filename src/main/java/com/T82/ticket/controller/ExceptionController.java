package com.T82.ticket.controller;

import com.T82.ticket.global.domain.exception.SeatNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(SeatNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String seatNotFoundExceptionHandler(SeatNotFoundException e){
        log.error("ERROR : SeatNotFoundException (BE404 - NOTFOUND)", e);
        return e.getMessage();
    }
}
