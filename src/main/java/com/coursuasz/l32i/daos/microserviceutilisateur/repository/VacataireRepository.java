package com.coursuasz.l32i.daos.microserviceutilisateur.repository;

import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Vacataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VacataireRepository extends JpaRepository<Vacataire, Long> {
    Vacataire findByUsername(String username);

    @Query("SELECT v FROM Vacataire v WHERE v.matricule = ?1")
    Optional<Vacataire> findByMatricule(String matricule);
}
