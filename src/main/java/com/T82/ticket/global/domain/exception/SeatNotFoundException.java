package com.T82.ticket.global.domain.exception;

public class SeatNotFoundException extends IllegalArgumentException{
    public SeatNotFoundException() {
        super("존재하지 않는 좌석 입니다.");
    }
}
