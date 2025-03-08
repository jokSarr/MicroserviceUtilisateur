package com.coursuasz.l32i.daos.microserviceutilisateur.controller;


import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceAlreadyExistException;
import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceNotFoundException;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Permanent;
import com.coursuasz.l32i.daos.microserviceutilisateur.service.PermanentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/permanents")
@RequiredArgsConstructor
@Slf4j
public class PermanentController {

    private final PermanentService permanentService;

    @PostMapping("/permanent")
    public ResponseEntity<?> ajouter(@RequestBody Permanent permanent) {
        try {
            permanentService.ajouter(permanent);
            return ResponseEntity.status(HttpStatus.CREATED).body("Permanent ajouté avec succès !");
        } catch (ResourceAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout.");
        }
    }

    @GetMapping("/permanent/{id}")
    public ResponseEntity<?> rechercher(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(permanentService.rechercher(id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/liste")
    public ResponseEntity<List<Permanent>> lister() {
        return ResponseEntity.ok(permanentService.Liste());
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<?> modifier(@PathVariable Long id, @RequestBody Permanent updatedPermanent) {
        try {
            Permanent permanentModifie = permanentService.modifier(id, updatedPermanent);
            return ResponseEntity.ok(permanentModifie);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la modification");
        }
    }

    @DeleteMapping("/permanent/{id}")
    public ResponseEntity<?> supprimer(@PathVariable Long id) {
        try {
            permanentService.supprimer(id);
            return ResponseEntity.ok("Permanent supprimé !");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

