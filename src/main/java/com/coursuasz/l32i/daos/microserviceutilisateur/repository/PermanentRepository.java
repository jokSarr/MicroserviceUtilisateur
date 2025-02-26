package com.coursuasz.l32i.daos.microserviceutilisateur.repository;


import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Permanent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermanentRepository extends JpaRepository<Permanent, Long> {
    Permanent findByUsername(String username);

    @Query("SELECT p FROM Permanent p WHERE p.matricule = ?1")
    Optional<Permanent> findByMatricule(String matricule);
}