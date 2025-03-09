package com.coursuasz.l32i.daos.microserviceutilisateur.controller;

import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceAlreadyExistException;
import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceNotFoundException;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Vacataire;
import com.coursuasz.l32i.daos.microserviceutilisateur.service.VacataireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "", allowedHeaders = "")
@RestController
@RequestMapping("/chef-departement")
@RequiredArgsConstructor
@Slf4j
public class VacataireController {

    private final VacataireService vacataireService;

    @PostMapping("/ajoutvacataire")
    public ResponseEntity<?> ajouter(@RequestBody Vacataire vacataire) {
        try {
            vacataireService.ajouter(vacataire);
            return ResponseEntity.status(HttpStatus.CREATED).body("Vacataire ajouté avec succès !");
        } catch (ResourceAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout.");
        }
    }

    @GetMapping("/recherchevacataire/{id}")  //  Correction ici
    public ResponseEntity<?> rechercher(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(vacataireService.rechercher(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listevacataire")
    @PreAuthorize("hasAuthority('CHEF-DEPARTEMENT')")

    public ResponseEntity<List<Vacataire>> lister() {
        return ResponseEntity.ok(vacataireService.Liste());
    }

    @PutMapping("/modifiervacataire/{id}")
    public ResponseEntity<?> modifier(@PathVariable Long id, @RequestBody Vacataire updatedVacataire) {
        try {
            Vacataire vacataireModifie = vacataireService.modifier(id, updatedVacataire);
            return ResponseEntity.ok(vacataireModifie); // Retourne l'objet mis à jour
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la modification");
        }
    }

    @DeleteMapping("/supprimervacataire/{id}")
    public ResponseEntity<?> supprimer(@PathVariable Long id) {
        try {
            vacataireService.supprimer(id);
            return ResponseEntity.ok("vacataire supprimé !");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}