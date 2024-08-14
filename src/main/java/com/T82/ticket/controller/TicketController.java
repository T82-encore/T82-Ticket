package com.T82.ticket.controller;

import com.T82.ticket.dto.response.TicketResponseDto;
import com.T82.ticket.dto.response.UserResponseDto;
import com.T82.ticket.global.domain.dto.UserDto;
import com.T82.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TicketResponseDto> getValidTickets(@AuthenticationPrincipal UserDto userDto){
        return ticketService.getValidTickets(userDto);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getUsersByEventId(@PathVariable(name = "eventId") String eventId){
        return ticketService.getUsersByEventId(eventId);
    }
}
