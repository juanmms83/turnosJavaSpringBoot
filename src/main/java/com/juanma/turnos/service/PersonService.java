package com.juanma.turnos.service;

import com.juanma.turnos.api.dto.PersonResponseDTO;


public interface PersonService {
    PersonResponseDTO searchByDni(String dni);
}
