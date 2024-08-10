package com.T82.ticket.api;

import com.T82.common_exception.exception.qrcode.FailedGenerateQRCodeException;
import com.T82.common_exception.exception.seat.EventInfoNotFoundException;
import com.T82.common_exception.exception.seat.SeatNotFoundException;
import com.T82.ticket.dto.request.QRCodeRequestDto;
import com.T82.ticket.dto.request.SeatDetailRequest;
import com.T82.ticket.dto.response.EventInfoResponseDto;
import com.T82.ticket.dto.response.QRCodeResponseDto;
import com.T82.ticket.dto.response.SeatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class ApiFeign {
    private final FeignEventInfo feignEventInfo;
    private final FeignSeat feignSeat;
    private final FeignQRCode feignQRCode;
    public EventInfoResponseDto getEventInfo(Long eventId) {
        try {
            return feignEventInfo.getEventInfo(eventId);
        } catch (Exception e) {
            throw new EventInfoNotFoundException();
        }
    }
    public List<SeatResponseDto> getSeats(List<Long> req){
        try {
            return feignSeat.getSeats(new SeatDetailRequest(req));
        }catch (Exception e){
            throw new SeatNotFoundException();
        }
    }
    public QRCodeResponseDto uploadQRCode(String req){
        try {
            return feignQRCode.uploadQRCode(QRCodeRequestDto.toDto(req));
        }catch (Exception e){
            throw new FailedGenerateQRCodeException();
        }
    }
}
