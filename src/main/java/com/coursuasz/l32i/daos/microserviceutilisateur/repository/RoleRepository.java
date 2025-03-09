package com.coursuasz.l32i.daos.microserviceutilisateur.repository;

import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}