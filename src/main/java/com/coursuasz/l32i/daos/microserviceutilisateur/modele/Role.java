package com.coursuasz.l32i.daos.microserviceutilisateur.modele;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

    public Role() {}

    public Role(String role) {
        this.role = role;
    }
}