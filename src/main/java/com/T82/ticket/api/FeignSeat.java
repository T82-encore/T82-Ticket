package com.T82.ticket.api;

import com.T82.ticket.dto.request.SeatDetailRequest;
import com.T82.ticket.dto.response.SeatListResponseDto;
import com.T82.ticket.dto.response.SeatResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(name = "seat-service", url = "${seat.path}")
public interface FeignSeat {
    @PostMapping("/seats/detail")
    List<SeatResponseDto> getSeats(@RequestBody SeatDetailRequest req);

}