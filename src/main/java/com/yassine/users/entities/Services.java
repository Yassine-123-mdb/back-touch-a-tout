package com.yassine.users.entities;

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

    private String title;       // Titre du service
    private String description; // Description
    private double price;       // Prix
    private String category;    // Catégorie
    private int duration;       // Durée estimée en minutes
    private String notes;       // Notes spéciales
    private String image;       // Chemin de l'image

    // Relation ManyToOne avec User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
