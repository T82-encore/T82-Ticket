package com.T82.ticket.service;

import com.T82.common_exception.exception.seat.SeatNotFoundException;
import com.T82.common_exception.exception.user.UserNotFoundException;
import com.T82.ticket.api.ApiFeign;
import com.T82.ticket.dto.request.SeatRequestDto;
import com.T82.ticket.dto.request.TicketRequestDto;
import com.T82.ticket.dto.request.refundRequestDto;
import com.T82.ticket.dto.response.*;
import com.T82.ticket.global.domain.dto.UserDto;
import com.T82.ticket.global.domain.entity.Ticket;
import com.T82.ticket.global.domain.repository.TicketRepository;
import com.T82.ticket.utils.grpc.GrpcClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final ApiFeign apiFeign;
    private final TicketRepository ticketRepository;
    private final GrpcClientService grpcClientService;
    /**
     * 결제 후 Kafka로 예매결과, 결제결과정보 전송 후 처리
     */
//    @KafkaListener(topics = "paymentSuccess", groupId = "paySuccess-group")
    @Override
    public void saveTickets(TicketRequestDto req) {
        log.info("티켓 발급 시작");
        long start = System.currentTimeMillis();
        EventInfoResponseDto eventInfo = apiFeign.getEventInfo(req.eventId());
        log.info("이벤트 통신 시간 : {}", (System.currentTimeMillis() - start));
        // 좌석 ID 목록 생성
        long start1 = System.currentTimeMillis();
        List<Long> seatIdList = new ArrayList<>();
        req.items().forEach(item -> seatIdList.add((long) item.seatId()));
        // 좌석 정보 가져오기
        List<SeatResponseDto> seats = apiFeign.getSeats(seatIdList);
        // 좌석 정보와 요청 항목을 매칭하여 티켓 저장
        seats.forEach(seat -> {
            req.items()
                    .stream()
                    .filter(item -> item.seatId()==seat.seatId())
                    .forEach(item -> {
                        QRCodeResponseDto qrResponse = apiFeign.uploadQRCode(String.valueOf(item.seatId()));
                        ticketRepository.save(Ticket.toEntity(req, eventInfo, seat, item.amount(), qrResponse.fileUrl()));
                    });
        });
        long end = System.currentTimeMillis();
        log.info("좌석 통신 후 티켓 {}장 발급 소요시간 = {}",seats.size(), (end - start1));
        log.info("총 소요시간 = {}",(end - start));
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
    public List<TicketResponseDto> getValidTickets(UserDto userDto) {
        List<Ticket> allByUserId = ticketRepository.findAllValidTicketByUserId(userDto.getId(),LocalDateTime.now().minusHours(9));
        return allByUserId.stream().map(TicketResponseDto::from).toList();
    }

    @Override
    public List<UserResponseDto> getUsersByEventId(Long req) {
        return ticketRepository.findAllByEventId(req).stream().map(UserResponseDto::from).toList();
    }
}
