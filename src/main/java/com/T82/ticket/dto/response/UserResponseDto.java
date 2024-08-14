package com.T82.ticket.dto.response;

import com.T82.ticket.global.domain.entity.Ticket;

public record UserResponseDto(
        String userId
) {
     public static UserResponseDto from(Ticket ticket){
        return new UserResponseDto(ticket.getUserId());
    }
}
