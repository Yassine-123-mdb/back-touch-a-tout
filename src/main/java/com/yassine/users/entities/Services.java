package com.yassine.users.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Titre du service
    private String description; // Description du service
    private double price; // Prix du service
    private String category; // Catégorie du service
    private int duration; // Durée estimée (en minutes)
    private String notes; // Notes spéciales pour le service
    private String image; // URL ou chemin de l'image du service
    

}
