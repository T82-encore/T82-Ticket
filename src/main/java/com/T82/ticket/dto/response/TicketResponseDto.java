package com.T82.ticket.dto.response;

import com.T82.ticket.global.domain.entity.Ticket;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public record TicketResponseDto (
        Long ticketId,
        Long eventInfoId,
        String userId,
        Long seatId,
        String sectionName,
        Integer rowNum,
        Integer columnNum,
        boolean isRefund,
        String eventName,
        LocalDateTime eventStartTime,
        String paymentDate,
        int paymentAmount,
        String orderNum
        ) {
    public static TicketResponseDto from(Ticket ticket){
        return new TicketResponseDto(
                ticket.getTicketId(),
                ticket.getEventInfoId(),
                ticket.getUserId(),
                ticket.getSeatId(),
                ticket.getSectionName(),
                ticket.getRowNum(),
                ticket.getColumnNum(),
                ticket.isRefund(),
                ticket.getEventName(),
                convertToLocalDateTime(ticket.getEventStartTime()),
                ticket.getPaymentDate(),
                ticket.getPaymentAmount(),
                ticket.getOrderNum()
        );
    }
    private static LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
