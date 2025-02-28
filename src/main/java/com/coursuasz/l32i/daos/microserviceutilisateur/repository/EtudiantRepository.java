package com.coursuasz.l32i.daos.microserviceutilisateur.repository;

import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Etudiant;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Vacataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    @Query("SELECT e FROM Etudiant e WHERE e.matricule = ?1")
    Optional<Etudiant> findByMatricule(String matricule);
}