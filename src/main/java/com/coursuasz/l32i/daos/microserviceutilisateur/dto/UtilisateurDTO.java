package com.coursuasz.l32i.daos.microserviceutilisateur.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDTO {
    private String username;
    private String nom;
    private String prenom;
    private String role;
}