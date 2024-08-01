package com.T82.ticket.service;

import com.T82.ticket.dto.request.TicketRequestDto;
import com.T82.ticket.dto.request.refundRequestDto;
import com.T82.ticket.dto.response.TicketResponseDto;
import com.T82.ticket.global.domain.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {
    void saveTickets(TicketRequestDto req);
    void refundTicket(refundRequestDto req);
    Page<TicketResponseDto> getValidTickets(UserDto userDto, Pageable pageRequest);
}
