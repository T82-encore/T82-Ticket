package com.T82.ticket.api;

import com.T82.ticket.dto.request.QRCodeRequestDto;
import com.T82.ticket.dto.response.QRCodeResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "qrCode-service", url = "${qrCode.path}")
public interface FeignQRCode {
    @PostMapping()
    QRCodeResponseDto uploadQRCode(@RequestBody QRCodeRequestDto req);
}
