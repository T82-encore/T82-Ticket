package com.T82.ticket.service;

import com.T82.ticket.dto.request.TicketRequestDto;
import com.T82.ticket.dto.response.EventInfoResponseDto;

public interface TicketService {
    void saveTickets(TicketRequestDto req);
}
