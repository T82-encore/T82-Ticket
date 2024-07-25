package com.T82.ticket.service;

import com.T82.ticket.dto.request.TicketRequestDto;
import com.T82.ticket.dto.response.TicketResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {
    void saveTickets(TicketRequestDto req);
    Page<TicketResponseDto> getValidTickets(Pageable pageRequest);
}
