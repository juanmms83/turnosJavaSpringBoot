package com.juanma.turnos.service.impl;

import com.juanma.turnos.api.dto.BoardItemDTO;
import com.juanma.turnos.api.dto.TicketResponseDTO;
import com.juanma.turnos.person.Person;
import com.juanma.turnos.repository.TicketRepository;
import com.juanma.turnos.service.TicketService;
import com.juanma.turnos.ticket.Ticket;
import com.juanma.turnos.ticket.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final AtomicInteger counter = new AtomicInteger(10);
    private final SimpMessagingTemplate ws;

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

    @Override
    public TicketResponseDTO callNext(final int moduleNumber){
        Ticket t = ticketRepository.findFirstByStatusOrderByCreatedAtAsc(TicketStatus.CREATED).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay ticket pendientes"));
        t.setStatus(TicketStatus.CALLED);
        t.setCalledAt(Instant.now());
        t.setModuleNumber(moduleNumber);
        //hacer logica parapara enviar al websocket el ticket llamado
        BoardItemDTO boardItemDTO = new BoardItemDTO(t.getCode(),t.getModuleNumber(),
                t.getPerson().getName()+" "+t.getPerson().getLastName());
        ws.convertAndSend("/topic/board", boardItemDTO);
        return  buildTicketResponseDTO(t);
    }

    @Override
    public TicketResponseDTO serve(Long id){
        Ticket t = ticketRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No se ha encontrado el ticket"));
        t.setStatus(TicketStatus.SERVED);
        t.setServedAt(Instant.now());
        return buildTicketResponseDTO(t);
    }

    @Override
    public List<BoardItemDTO> lastCalled(final int limit){
        return ticketRepository.findTop10ByStatusOrderByCalledAtDesc(TicketStatus.CALLED).stream().limit(limit).map(t->new BoardItemDTO(
                t.getCode(),
                t.getModuleNumber(),
                t.getPerson().getName()+" "+t.getPerson().getLastName())
        ).toList();
    }

    private TicketResponseDTO buildTicketResponseDTO(Ticket ticket){
        return new TicketResponseDTO(ticket.getId(), ticket.getCode(),ticket.getStatus(),ticket.getCreatedAt(),
                ticket.getCalledAt(),ticket.getServedAt(),ticket.getModuleNumber(),
                ticket.getPerson().getName()+" "+ticket.getPerson().getLastName());
    }
}
