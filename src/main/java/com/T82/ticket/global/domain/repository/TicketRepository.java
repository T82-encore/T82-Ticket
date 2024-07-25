package com.T82.ticket.global.domain.repository;

import com.T82.ticket.global.domain.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.userId = :userId AND t.eventStartTime > :currentDate ORDER BY t.eventStartTime ASC")
    Page<Ticket> findAllValidTicketByUserId(@Param("userId") String userId, @Param("currentDate") Date currentDate, Pageable pageRequest);
}
