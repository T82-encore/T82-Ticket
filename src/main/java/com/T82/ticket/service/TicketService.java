package com.T82.ticket.service;

import com.T82.ticket.dto.request.TicketRequestDto;

public interface TicketService {
    void saveTickets(TicketRequestDto req);
}
