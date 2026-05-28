package com.juanma.turnos.service;


import com.juanma.turnos.api.dto.TicketResponseDTO;
import com.juanma.turnos.person.Person;

public interface TicketService {
    public TicketResponseDTO create(Person person);
}