package com.T82.ticket.dto.request;

import java.util.List;

public record SeatListRequestDto(
List<String> seatIds
) {}