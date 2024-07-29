package com.T82.ticket.dto.request;

import java.util.List;

public record TicketRequestDto(
        String eventId, String userId, String orderNo, String paymentDate, List<SeatRequestDto> items
) {
}
