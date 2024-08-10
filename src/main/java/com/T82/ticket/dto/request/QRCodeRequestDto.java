package com.T82.ticket.dto.request;

import lombok.Builder;

@Builder
public record QRCodeRequestDto (String seatId){
    public static QRCodeRequestDto toDto(String seatId){
        return QRCodeRequestDto.builder()
                .seatId(seatId)
                .build();
    }
}
