package com.example.examenjpapersistence.model;

import lombok.*;
import javax.persistence.*;  // Retour à javax.persistence
import java.time.LocalDate;

@Entity
@Table(name = "produit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // <-- Utilisation de Long au lieu de int

    @Column(nullable = false)
    private String libelle;

    @Column(nullable = false)
    private double quantite;

    @Column(nullable = false)
    private double prixUnitaire;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCategorie", nullable = false)
    private Categorie categorie;

    @Column(name = "date_creation")
    private LocalDate dateCreation;
}
