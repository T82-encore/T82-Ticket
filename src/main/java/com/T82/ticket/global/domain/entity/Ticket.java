package com.T82.ticket.global.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

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
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "SEAT_ID")
    private Long seatId;
    @Column(name = "SECTION_NAME")
    private String sectionName;
    @Column(name = "ROW_NUM")
    private Integer rowNum;
    @Column(name = "COLUMN_NUM")
    private Integer columnNum;
    @Column(name = "IS_REFUND")
    private boolean isRefund;
    @Column(name = "EVENT_NAME")
    private String eventName;
    @Column(name = "EVENT_START_TIME")
    private Date eventStartTime;
    @Column(name = "PAYMENT_DATE")
    private Date paymentDate;
    @Column(name = "PAYMENT_AMOUNT")
    private Long paymentAmount;
    @Column(name = "ORDER_NUM")
    private String orderNum;
}