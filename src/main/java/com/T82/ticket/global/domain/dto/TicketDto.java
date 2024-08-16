package com.T82.ticket.global.domain.dto;


import java.time.LocalDateTime;

public record TicketDto (
        Long ticketId,
        Long eventInfoId,
        String userId,
        String sectionName,
        String seatId,
        int seatRowNumber,
        int seatColumnNumber,
        boolean isRefund,
        String eventName,
        LocalDateTime eventStartTime,
        String paymentDate,
        int amount,
        String orderNum
        ){

}
