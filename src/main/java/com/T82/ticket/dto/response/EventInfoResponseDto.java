package com.T82.ticket.dto.response;

import java.util.Date;

public record EventInfoResponseDto(
        Long eventInfoId, String title, Date eventStartTime
) {
}
