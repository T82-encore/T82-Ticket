package com.T82.ticket.service;

import com.T82.ticket.api.ApiFeign;
import com.T82.ticket.dto.request.SeatRequestDto;
import com.T82.ticket.dto.request.TicketRequestDto;
import com.T82.ticket.dto.request.refundRequestDto;
import com.T82.ticket.dto.response.EventInfoResponseDto;
import com.T82.ticket.dto.response.SeatResponseDto;
import com.T82.ticket.dto.response.TicketResponseDto;
import com.T82.ticket.global.domain.dto.UserDto;
import com.T82.ticket.global.domain.entity.Ticket;
import com.T82.ticket.global.domain.exception.SeatNotFoundException;
import com.T82.ticket.global.domain.repository.TicketRepository;
import com.T82.ticket.utils.ByteArrayMultipartFile;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final ApiFeign apiFeign;
    private final TicketRepository ticketRepository;
    private final QRCodeService qrCodeService;
    private final FileUploadService fileUploadService;
    /**
     * 결제 후 Kafka로 예매결과, 결제결과정보 전송 후 처리
     */
    @KafkaListener(topics = "paymentSuccess", groupId = "paySuccess-group")
    @Override
    @Transactional
    public void saveTickets(TicketRequestDto req) {
        log.info("paymentSuccess = {}",req.toString());
        EventInfoResponseDto eventInfo = apiFeign.getEventInfo(req.eventId());
        // 좌석 ID 목록 생성
        List<Long> seatIdList = new ArrayList<>();
        req.items().forEach(item -> seatIdList.add((long) item.seatId()));
        // 좌석 정보 가져오기
        List<SeatResponseDto> seats = apiFeign.getSeats(seatIdList);
        // 좌석 정보와 요청 항목을 매칭하여 티켓 저장
        seats.forEach(seat -> {
            req.items().stream()
                    .filter(item -> item.seatId()==seat.seatId())
                    .forEach(item -> {
                        // qr코드 생성
                        MultipartFile multipartFile = createQRCode(req, item);
                        // QRcode를 S3에 저장
                        String qrCodeUrl = uploadQRCode(multipartFile);
                        ticketRepository.save(Ticket.toEntity(req, eventInfo, seat, item.amount(),qrCodeUrl));
                    });
        });
    }

    private String uploadQRCode(MultipartFile multipartFile) {
        String qrCodeUrl = null;
        try {
            qrCodeUrl = fileUploadService.save(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return qrCodeUrl;
    }

    private MultipartFile createQRCode(TicketRequestDto req, SeatRequestDto item) {
        byte[] qrCodeData = new byte[0];
        try {
            qrCodeData = qrCodeService.generateQRCode(String.valueOf(req.eventId()),200, 200);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
        String fileName = String.format("qr_%s_%d.png", req.eventId(), item.seatId());
        MultipartFile multipartFile = new ByteArrayMultipartFile(fileName, qrCodeData);
        return multipartFile;
    }

    /**
     * 환불시 Kafka로 seatId를 받아서 해당 쿠폰 삭제
     */
    @KafkaListener(topics = "refundTicket", groupId = "refund-group")
    @Override
    @Transactional
    public void refundTicket(refundRequestDto req) {
        log.info("kafka seatId = {}",req.seatId());
        Ticket bySeatId = ticketRepository.findBySeatId(req.seatId()).orElseThrow(SeatNotFoundException::new);
        bySeatId.refundTicket();
    }

    /**
     * 쓸 수 있는 티켓 반환
     */
    @Override
    @Transactional
    public Page<TicketResponseDto> getValidTickets(UserDto userDto, Pageable pageRequest) {
        Page<Ticket> allByUserId = ticketRepository.findAllValidTicketByUserId(userDto.getId(),new Date(), pageRequest);
        return allByUserId.map(TicketResponseDto::from);
    }

}
