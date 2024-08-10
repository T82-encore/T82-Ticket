package com.T82.ticket.api;

import com.T82.ticket.dto.request.SeatDetailRequest;
import com.T82.ticket.dto.request.SeatListRequestDto;
import com.T82.ticket.dto.response.EventInfoResponseDto;
import com.T82.ticket.dto.response.SeatListResponseDto;
import com.T82.ticket.dto.response.SeatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApiFeign {
    private final FeignEventInfo feignEventInfo;
    private final FeignSeat feignSeat;
    public final static List<Map<String, Object>> failList
            = new ArrayList<>();
    public EventInfoResponseDto getEventInfo(Long eventId) {
        try {
            return feignEventInfo.getEventInfo(eventId);
        } catch (Exception e) {
            Map<String, Object> map = new HashMap<>();
            map.put("eventId", eventId);
            failList.add(map);
            return null;
        }
    }
    public List<SeatResponseDto> getSeats(List<Long> req){
        try {
            return feignSeat.getSeats(new SeatDetailRequest(req));
        }catch (Exception e){
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("first", req.get(0));
            failList.add(map);
            return new SeatListResponseDto(null).req();
        }
    }
}
