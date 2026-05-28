package com.juanma.turnos.api;

import com.juanma.turnos.api.dto.PersonResponseDTO;
import com.juanma.turnos.api.dto.TicketResponseDTO;
import com.juanma.turnos.person.Person;
import com.juanma.turnos.repository.PersonRepository;
import com.juanma.turnos.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final PersonRepository personRepository;

    @PostMapping
    public TicketResponseDTO create(@RequestBody final PersonResponseDTO  dto){
        Person person = personRepository.findByDni(dto.dni()).orElseGet(()->{
            Person p = new Person();
            p.setDni(dto.dni());
            p.setName(dto.firstName());
            p.setName(dto.lastName());
            return personRepository.save(p);
        });

        return ticketService.create(person);
    }
}