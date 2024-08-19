package com.T82.ticket.global.domain.entity;

import com.T82.ticket.dto.request.TicketRequestDto;
import com.T82.ticket.dto.response.EventInfoResponseDto;
import com.T82.ticket.dto.response.SeatResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.t82.event.lib.GetEventReply;
import org.t82.seat.lib.SeatDetailResponse;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TICKETS")
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TICKET_ID")
    private Long ticketId;
    @Column(name = "EVENTINFO_ID")
    private Long eventInfoId;
    @Column(name = "EVENT_ID")
    private Long eventId;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "SEAT_ID")
    private Long seatId;
    @Column(name = "SECTION_NAME")
    private String sectionName;
    @Column(name = "ROW_NUM")
    private Integer rowNum;
    @Column(name = "COLUMN_NUM")
    private Integer columnNum;
    @Column(name = "IS_REFUND") @Setter
    private boolean isRefund;
    @Column(name = "EVENT_NAME")
    private String eventName;
    @Column(name = "EVENT_START_TIME")
    private LocalDateTime eventStartTime;
    @Column(name = "IMAGE_URL")
    private String imageUrl;
    @Column(name = "PAYMENT_DATE")
    private String paymentDate;
    @Column(name = "PAYMENT_AMOUNT")
    private int paymentAmount;
    @Column(name = "ORDER_NUM")
    private String orderNum;
    @Column(name = "QRCODE_URL")
    private String qrCodeUrl;

    public void refundTicket(){
        this.isRefund = true;
    }

    public static Ticket toEntity(TicketRequestDto req, EventInfoResponseDto eventInfo, SeatResponseDto seat, int amount,String qrCodeUrl) {

        return Ticket.builder()
                .userId(req.userId())
                .eventInfoId(eventInfo.eventInfoId())
                .sectionName(seat.seatSection())
                .seatId(seat.seatId())
                .rowNum(seat.seatRowNumber())
                .columnNum(seat.seatColumnNumber())
                .isRefund(false)
                .eventName(eventInfo.title())
                .eventStartTime(eventInfo.eventStartTime().toLocalDateTime())
                .paymentDate(req.paymentDate())
                .paymentAmount(amount)
                .orderNum(req.orderNo())
                .qrCodeUrl(qrCodeUrl)
                .eventId(req.eventId())
                .build();
    }
    public static Ticket toEntity(TicketRequestDto req, GetEventReply eventInfo, SeatDetailResponse seat, int amount, String qrCodeUrl) {

        return Ticket.builder()
                .userId(req.userId())
                .eventInfoId(eventInfo.getEventInfoId())
                .sectionName(seat.getSection())
                .seatId(seat.getId())
                .rowNum(seat.getRowNum())
                .columnNum(seat.getColNum())
                .isRefund(false)
                .eventName(eventInfo.getTitle())
                .eventStartTime(LocalDateTime.parse(eventInfo.getEventStartTime().replace(" ","T")).minusHours(9))
                .imageUrl(eventInfo.getImageUrl())
                .paymentDate(req.paymentDate())
                .paymentAmount(amount)
                .orderNum(req.orderNo())
                .qrCodeUrl(qrCodeUrl)
                .eventId(req.eventId())
                .build();
    }
}