package com.T82.ticket.dto.response;

import com.T82.ticket.global.domain.entity.Ticket;

import java.sql.Timestamp;

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
        Timestamp eventStartTime,
        String paymentDate,
        int paymentAmount,
        String orderNum,
        String qrCodeUrl
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
                ticket.getEventStartTime(),
                ticket.getPaymentDate(),
                ticket.getPaymentAmount(),
                ticket.getOrderNum(),
                ticket.getQrCodeUrl()
        );
    }
}
