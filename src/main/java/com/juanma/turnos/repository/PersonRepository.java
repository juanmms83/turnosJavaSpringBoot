package com.juanma.turnos.repository;

import com.juanma.turnos.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByDni(String dni);
}