package com.juanma.turnos.service;


import com.juanma.turnos.api.dto.BoardItemDTO;
import com.juanma.turnos.api.dto.TicketResponseDTO;
import com.juanma.turnos.person.Person;

import java.util.List;

public interface TicketService {
    TicketResponseDTO create(Person person);
    TicketResponseDTO callNext(final int moduleNumber);
    TicketResponseDTO serve(Long id);
    List<BoardItemDTO> lastCalled(final int limit);
}