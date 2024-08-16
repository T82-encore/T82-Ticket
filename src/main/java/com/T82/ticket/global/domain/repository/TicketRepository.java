package com.T82.ticket.global.domain.repository;

import com.T82.ticket.global.domain.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.userId = :userId AND t.eventStartTime > :currentDate AND t.isRefund = false ORDER BY t.eventStartTime ASC")
    List<Ticket> findAllValidTicketByUserId(@Param("userId") String userId, @Param("currentDate") LocalDateTime currentDate);

    Optional<Ticket> findBySeatId(Long seatId);

    List<Ticket> findAllByEventId(Long eventId);
}
