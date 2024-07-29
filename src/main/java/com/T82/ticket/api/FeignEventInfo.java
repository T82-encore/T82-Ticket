package com.T82.ticket.api;

import com.T82.ticket.dto.response.EventInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "evnetInfo-service", url = "${eventInfo.path}")
public interface FeignEventInfo {
    @GetMapping("/contents/{eventId}/details")
    EventInfoResponseDto getEventInfo(@PathVariable("eventId") String eventId);
}