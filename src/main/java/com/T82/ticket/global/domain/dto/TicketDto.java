package com.T82.ticket.global.domain.dto;


import java.sql.Timestamp;

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
        Timestamp eventStartTime,
        String paymentDate,
        int amount,
        String orderNum
        ){

}
