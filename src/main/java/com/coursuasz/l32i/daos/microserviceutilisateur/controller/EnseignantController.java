package com.coursuasz.l32i.daos.microserviceutilisateur.controller;

import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceAlreadyExistException;
import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceNotFoundException;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Enseignant;
import com.coursuasz.l32i.daos.microserviceutilisateur.repository.EnseignantRepository;
import com.coursuasz.l32i.daos.microserviceutilisateur.service.EnseignantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/enseignants")
@RequiredArgsConstructor
@Slf4j
public class EnseignantController {

    private final EnseignantService enseignantService;

    private final EnseignantRepository enseignantRepository;

    //ajout enseignant
    @PostMapping("/ajouter")
    public ResponseEntity<?> ajouter(@RequestBody Enseignant enseignant) {
        try {
            enseignantService.ajouter(enseignant);
            return ResponseEntity.status(HttpStatus.CREATED).body("Enseignant ajouté avec succès !");
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
            return ResponseEntity.ok(enseignantService.rechercherParId(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //liste
    @GetMapping("/liste")
    public ResponseEntity<List<Enseignant>> lister() {
        return ResponseEntity.ok(enseignantService.lister());
    }

    //modification
    @PutMapping("/modifier/{id}")
    public ResponseEntity<?> modifier(@PathVariable Long id, @RequestBody Enseignant updatedEnseignant) {
        try {
            Enseignant enseignantModifie = enseignantService.modifier(id, updatedEnseignant);
            return ResponseEntity.ok(enseignantModifie); // Retourne l'objet mis à jour
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
            enseignantService.supprimer(id);
            return ResponseEntity.ok("Enseignant supprimé !");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //activation
    @PutMapping("/activer/{id}")
    public ResponseEntity<?> activer(@PathVariable Long id) {
        try {
            enseignantService.activer(id);
            return ResponseEntity.ok("Statut activé/désactivé !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du changement de statut.");
        }
    }

    //archiver enseignant
    @PutMapping("/archiver/{id}")
    public ResponseEntity<?> archiver(@PathVariable Long id) {
        try {
            enseignantService.archiver(id);
            return ResponseEntity.ok("Statut archivé/désarchivé !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'archivage.");
        }
    }
}


