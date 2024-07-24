package com.T82.ticket.global.domain.dto;

import com.T82.ticket.dto.request.TicketRequestDto;
import com.T82.ticket.dto.response.EventInfoResponseDto;
import com.T82.ticket.dto.response.SeatResponseDto;
import com.T82.ticket.global.domain.entity.Ticket;

import java.util.Date;

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
        Date eventStartTime,
        String paymentDate,
        int amount,
        String orderNum
        ){

}
