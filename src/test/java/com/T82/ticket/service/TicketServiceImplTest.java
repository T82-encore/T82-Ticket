package com.T82.ticket.service;

import com.T82.ticket.global.domain.entity.Ticket;
import com.T82.ticket.global.domain.exception.SeatNotFoundException;
import com.T82.ticket.global.domain.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TicketServiceImplTest {
    @Autowired
    TicketServiceImpl ticketService;

    @Autowired
    TicketRepository ticketRepository;

    @BeforeEach
    void setUp() {
    }

    @Nested
    @Transactional
    class 티켓환불 {
        @Test
        void 티켓환불_성공 () {
            // given
            Long seatId = 1L;
            Ticket ticket = Ticket.builder()
                    .seatId(seatId)
                    .userId("testUser")
                    .eventInfoId(1L)
                    .sectionName("A")
                    .rowNum(1)
                    .columnNum(1)
                    .isRefund(false)
                    .eventName("Test Event")
                    .eventStartTime(new Date())
                    .paymentDate("2024-07-31")
                    .paymentAmount(100)
                    .orderNum("12345")
                    .build();
            ticketRepository.save(ticket);

            // when
            ticketService.refundTicket(seatId);

            // then
            Ticket refundedTicket = ticketRepository.findBySeatId(seatId).orElseThrow(SeatNotFoundException::new);
            assertTrue(refundedTicket.isRefund());
        }

        @Test
        void 티켓환불에러_없는좌석일때() {
            // given
            Long nonExistentSeatId = 999L;

            // when & then
            assertThrows(SeatNotFoundException.class, () -> {
                ticketService.refundTicket(nonExistentSeatId);
            });
        }
    }
}