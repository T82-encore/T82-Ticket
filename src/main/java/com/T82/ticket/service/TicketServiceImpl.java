package com.T82.ticket.service;

import com.T82.ticket.api.ApiFeign;
import com.T82.ticket.dto.request.TicketRequestDto;
import com.T82.ticket.dto.response.EventInfoResponseDto;
import com.T82.ticket.dto.response.SeatResponseDto;
import com.T82.ticket.dto.response.TicketResponseDto;
import com.T82.ticket.global.domain.dto.UserDto;
import com.T82.ticket.global.domain.entity.Ticket;
import com.T82.ticket.global.domain.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final ApiFeign apiFeign;
    private final TicketRepository ticketRepository;
    /**
     * 결제 후 Kafka로 예매결과, 결제결과정보 전송 후 처리
     */
    @KafkaListener(topics = "paymentSuccess")
    @Override
    @Transactional
    public void saveTickets(TicketRequestDto req) {
        log.error("Kafka Log {}", req.toString());

        EventInfoResponseDto eventInfo = apiFeign.getEventInfo(req.eventId());
        // 좌석 ID 목록 생성
        List<Long> seatIdList = new ArrayList<>();
        req.items().forEach(item -> seatIdList.add((long) item.seatId()));
        // 좌석 정보 가져오기
        List<SeatResponseDto> seats = apiFeign.getSeats(seatIdList);
        log.error("feign {}", seats.toString());
        // 좌석 정보와 요청 항목을 매칭하여 티켓 저장
        seats.forEach(seat -> {
            req.items().stream()
                    .filter(item -> item.seatId()==seat.seatId())
                    .forEach(item -> {
                        Ticket ticket = Ticket.toEntity(req, eventInfo, seat, item.amount());
                        log.error("before save {}", ticket.toString());
                        ticketRepository.save(ticket);
                    });
        });

    }

    @Override
    @Transactional
    public Page<TicketResponseDto> getValidTickets(UserDto userDto, Pageable pageRequest) {
        Page<Ticket> allByUserId = ticketRepository.findAllValidTicketByUserId(userDto.getId(),new Date(), pageRequest);
        return allByUserId.map(TicketResponseDto::from);
    }

}
