package com.T82.ticket.service;

import com.T82.ticket.api.ApiFeign;
import com.T82.ticket.dto.request.SeatRequestDto;
import com.T82.ticket.dto.request.TicketRequestDto;
import com.T82.ticket.dto.response.EventInfoResponseDto;
import com.T82.ticket.dto.response.SeatResponseDto;
import com.T82.ticket.global.domain.entity.Ticket;
import com.T82.ticket.global.domain.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@EnableKafka
public class TicketServiceImplTest {

    @Mock
    private ApiFeign apiFeign;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    public void testSaveTickets() throws Exception {
        // 준비 작업
        SeatRequestDto seatRequestDto = new SeatRequestDto(1, 100);
        TicketRequestDto req = new TicketRequestDto(13L, "user456", "order789", "2024-08-01", List.of(seatRequestDto));
        EventInfoResponseDto eventInfo = mock(EventInfoResponseDto.class);
        SeatResponseDto seat = mock(SeatResponseDto.class);

        when(seat.seatId()).thenReturn(1L); // seatId 설정
        when(eventInfo.eventInfoId()).thenReturn(1L); // eventInfoId 설정

        byte[] qrCodeData = new byte[1]; // 예제 데이터
        String qrCodeUrl = "https://example.com/qr.png";

        when(apiFeign.getEventInfo(13L)).thenReturn(eventInfo);
        when(apiFeign.getSeats(List.of(1L))).thenReturn(List.of(seat));

        // 테스트 실행
        ticketService.saveTickets(req);

        // 검증
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

}