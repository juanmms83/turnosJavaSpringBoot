package com.juanma.turnos.service.impl;

import com.juanma.turnos.api.dto.PersonResponseDTO;
import com.juanma.turnos.person.Person;
import com.juanma.turnos.repository.PersonRepository;
import com.juanma.turnos.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    public PersonResponseDTO searchByDni(String dni) {

        if(!dni.matches("\\d{8}")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid DNI");
        }

        Optional<Person> person = personRepository.findByDni(dni);

        if (person.isPresent()) {
            return new PersonResponseDTO(person.get().getDni(), person.get().getName(), person.get().getLastName());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
    }
}
