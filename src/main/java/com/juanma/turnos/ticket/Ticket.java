package com.juanma.turnos.ticket;

import com.juanma.turnos.person.Person;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "ticket", indexes = {@Index(columnList = "status"), @Index(columnList = "calledAt")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String code;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    private Instant createdAt;
    private Instant calledAt;
    private Instant servedAt;
    private Integer moduleNumber;
    @ManyToOne
    private Person person;

}
