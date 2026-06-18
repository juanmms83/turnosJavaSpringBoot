package com.juanma.turnos.api.dto;

import com.juanma.turnos.ticket.TicketStatus;

import java.time.Instant;

public record TicketResponseDTO(Long id, String code, TicketStatus status, Instant createAt, Instant calledAt,
                                Instant serveAt, Integer moduleNumber, String fullName) {
}
