package com.T82.ticket.dto.response;

import java.sql.Timestamp;
import java.util.Date;

public record EventInfoResponseDto(
        Long eventInfoId, String title, Timestamp eventStartTime
) {
}
