package com.yassine.users.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // L'utilisateur qui réserve (client)

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "service_id")
    private Services service;  // Le service réservé

    @Temporal(TemporalType.DATE)
    private Date reservationDate;  // La date de la réservation

    @Temporal(TemporalType.TIME)
    private Date reservationTime;  // L'heure de la réservation

    private String status;  // Le statut de la réservation (confirmée, en attente, etc.)
}
