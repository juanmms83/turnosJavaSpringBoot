package com.juanma.turnos.api;

import com.juanma.turnos.api.dto.PersonResponseDTO;
import com.juanma.turnos.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PersonController {

    private final PersonService personService;

    @GetMapping("/searchByDNI/{dni}")
    public PersonResponseDTO searchByDni(@PathVariable (name = "dni")final String dni) {
        return personService.searchByDni(dni);
    }
}
