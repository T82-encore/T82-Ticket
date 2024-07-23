package com.T82.ticket.dto.request;

import java.time.LocalDateTime;
import java.util.List;

public record TicketRequestDto(
        String evnetId, String userId, String orderNo, LocalDateTime paymentDate, List<SeatRequestDto> items
) {
}
