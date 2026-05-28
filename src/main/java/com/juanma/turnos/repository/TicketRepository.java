package com.juanma.turnos.repository;

import com.juanma.turnos.ticket.Ticket;
import com.juanma.turnos.ticket.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findFirstByStatusOrderByCreatedAtAsc(TicketStatus status);
    List<Ticket> findTop10ByStatusOrderByCalledAtDesc(TicketStatus status);
}
