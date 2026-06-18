package com.juanma.turnos.api;

import com.juanma.turnos.api.dto.BoardItemDTO;
import com.juanma.turnos.api.dto.PersonResponseDTO;
import com.juanma.turnos.api.dto.TicketResponseDTO;
import com.juanma.turnos.person.Person;
import com.juanma.turnos.repository.PersonRepository;
import com.juanma.turnos.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final PersonRepository personRepository;

    @PostMapping
    public TicketResponseDTO create(@RequestBody final PersonResponseDTO dto) {
        Person person = personRepository.findByDni(dto.dni()).orElseGet(() -> {
            Person p = new Person();
            p.setDni(dto.dni());
            p.setName(dto.firstName());
            p.setLastName(dto.lastName());
            return personRepository.save(p);
        });
        if(!dto.dni().matches("\\d{8}")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El DNI debe de contener 8 digitos como minimo");
        }
        return ticketService.create(person);
    }

    @GetMapping("/next")
    public TicketResponseDTO next(@RequestParam(name = "module") int module) {
        return ticketService.callNext(module);
    }

    @PostMapping("/{id}/serve")
    public TicketResponseDTO serve(@PathVariable(name = "id") final Long id) {
        return ticketService.serve(id);
    }

    @GetMapping("/board/last")
    public List<BoardItemDTO> lastCalled(@RequestParam(name = "limit")int limit){
        return ticketService.lastCalled(limit);
    }
}