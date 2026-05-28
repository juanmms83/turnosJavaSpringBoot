package com.juanma.turnos.service.impl;

import com.juanma.turnos.api.dto.TicketResponseDTO;
import com.juanma.turnos.person.Person;
import com.juanma.turnos.repository.TicketRepository;
import com.juanma.turnos.service.TicketService;
import com.juanma.turnos.ticket.Ticket;
import com.juanma.turnos.ticket.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final AtomicInteger counter = new AtomicInteger(10);

    @Override
    public TicketResponseDTO create(Person person) {
        char prefix = 'A';
        while(true){
            String code = prefix + String.valueOf(counter.getAndIncrement());
            try{
                Ticket ticket = Ticket.builder()
                        .code(code)
                        .status(TicketStatus.CREATED)
                        .createdAt(Instant.now())
                        .person(person)
                        .build();
                return buildTicketResponseDTO(ticketRepository.save(ticket));
            }catch (DataIntegrityViolationException ex){
                //retry witch other code

            }
        }
    }

    private TicketResponseDTO buildTicketResponseDTO(Ticket ticket){
        return new TicketResponseDTO(ticket.getId(), ticket.getCode(),ticket.getStatus(),ticket.getCreatedAt(),
                ticket.getCalledAt(),ticket.getServedAt(),ticket.getModuleNumber(),
                ticket.getPerson()+" "+ticket.getPerson().getLastName());
    }
}
