package com.coursuasz.l32i.daos.microserviceutilisateur.controller;

import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceAlreadyExistException;
import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceNotFoundException;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Etudiant;
import com.coursuasz.l32i.daos.microserviceutilisateur.service.EtudiantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "", allowedHeaders = "")
@RestController
@RequestMapping("/etudiants")
@RequiredArgsConstructor
@Slf4j
public class EtudiantController {

    private final EtudiantService etudiantService;

    //ajout etudiant
    @PostMapping("/ajouter")
    public ResponseEntity<?> ajouter(@RequestBody Etudiant etudiant) {
        try {
            etudiantService.ajouter(etudiant);
            return ResponseEntity.status(HttpStatus.CREATED).body("etudiant ajouté avec succès !");
        } catch (ResourceAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout.");
        }
    }

    //rcherche
    @GetMapping("/{id}")
    public ResponseEntity<?> rechercher(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(etudiantService.rechercher(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //liste
    @GetMapping("/liste")
    public ResponseEntity<List<Etudiant>> lister() {
        return ResponseEntity.ok(etudiantService.lister());
    }

    //modification
    @PutMapping("/modifier/{id}")
    public ResponseEntity<?> modifier(@PathVariable Long id, @RequestBody Etudiant updatedEtudiant) {
        try {
            Etudiant etudiantModifie = etudiantService.modifier(id, updatedEtudiant);
            return ResponseEntity.ok(etudiantModifie); // Retourne l'objet mis à jour
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la modification");
        }
    }

    //supression
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<?> supprimer(@PathVariable Long id) {
        try {
            etudiantService.supprimer(id);
            return ResponseEntity.ok("Etudiant supprimé !");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
