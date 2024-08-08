package com.T82.ticket.global.domain.entity;

import com.T82.ticket.dto.request.TicketRequestDto;
import com.T82.ticket.dto.response.EventInfoResponseDto;
import com.T82.ticket.dto.response.SeatResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.t82.event.lib.GetEventReply;

import java.time.LocalDateTime;
import java.util.Date;

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
    private Date eventStartTime;
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
                .eventStartTime(eventInfo.eventStartTime())
                .paymentDate(req.paymentDate())
                .paymentAmount(amount)
                .orderNum(req.orderNo())
                .qrCodeUrl(qrCodeUrl)
                .build();
    }
    public static Ticket toEntity(TicketRequestDto req, GetEventReply eventInfo, SeatResponseDto seat, int amount, String qrCodeUrl) {

        return Ticket.builder()
                .userId(req.userId())
                .eventInfoId(eventInfo.getEventInfoId())
                .sectionName(seat.seatSection())
                .seatId(seat.seatId())
                .rowNum(seat.seatRowNumber())
                .columnNum(seat.seatColumnNumber())
                .isRefund(false)
                .eventName(eventInfo.getTitle())
                .eventStartTime(java.sql.Timestamp.valueOf(LocalDateTime.parse(eventInfo.getEventStartTime())))
                .paymentDate(req.paymentDate())
                .paymentAmount(amount)
                .orderNum(req.orderNo())
                .qrCodeUrl(qrCodeUrl)
                .build();
    }
}