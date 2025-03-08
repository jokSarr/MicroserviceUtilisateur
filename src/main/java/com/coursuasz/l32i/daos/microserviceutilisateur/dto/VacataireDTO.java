package com.coursuasz.l32i.daos.microserviceutilisateur.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VacataireDTO {
    private String username;
    private String nom;
    private String prenom;
    private String niveau;
}
