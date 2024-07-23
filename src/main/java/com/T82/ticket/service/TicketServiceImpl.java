package com.T82.ticket.service;

import com.T82.ticket.dto.request.TicketRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    /**
     * 결제 후 Kafka로 예매결과, 결제결과정보 전송 후 처리
     */
    @KafkaListener(topics = "ticketPayment")
    @Transactional
    public void saveTickets(TicketRequestDto req) {

    }
}
