package com.coursuasz.l32i.daos.microserviceutilisateur.repository;

import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByUsername(String username);
}