package com.juanma.turnos.person;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "person", indexes = {@Index(columnList = "dni", unique = true)})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 8, nullable = false, unique = true)
    private String dni;
    private String name;
    private String lastName;
}
