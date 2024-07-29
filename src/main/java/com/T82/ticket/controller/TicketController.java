package com.T82.ticket.controller;

import com.T82.ticket.dto.response.TicketResponseDto;
import com.T82.ticket.global.domain.dto.UserDto;
import com.T82.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<TicketResponseDto> getValidTickets(
            @PageableDefault(size =5, page = 0,sort = "eventStartTime", direction = Sort.Direction.ASC) Pageable pageRequest,
            @AuthenticationPrincipal UserDto userDto
            ){
        return ticketService.getValidTickets(userDto, pageRequest);
    }
}
