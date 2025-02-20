package com.coursuasz.l32i.daos.microserviceutilisateur.modele;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String nom;
    private String prenom;
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    private boolean active;
    private String role;

}