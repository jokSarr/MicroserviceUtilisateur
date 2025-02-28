package com.coursuasz.l32i.daos.microserviceutilisateur.service;

import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceAlreadyExistException;
import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceNotFoundException;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Etudiant;
import com.coursuasz.l32i.daos.microserviceutilisateur.repository.EtudiantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;

    public void ajouter(Etudiant etudiant) {
        Optional<Etudiant> existe = etudiantRepository.findByMatricule(etudiant.getMatricule());
        if (existe.isPresent()) {
            throw new ResourceAlreadyExistException("Le username " + etudiant.getId() + " existe déjà");
        }
        try {
            etudiantRepository.save(etudiant);
        } catch (Exception exception) {
            throw new RuntimeException("Erreur lors de l'ajout", exception);
        }
    }

    public Etudiant rechercher(Long id) {
        return etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant avec ID " + id + " introuvable"));
    }

    public List<Etudiant> lister() {
        return etudiantRepository.findAll();
    }

    public void modifier(Long id, Etudiant updatedEtudiant) {
        Etudiant etudiant = rechercher(id);
        etudiant.setMatricule(updatedEtudiant.getMatricule());
        etudiantRepository.save(etudiant);
    }

    public void supprimer(Long id) {
        Etudiant etudiant = rechercher(id);
        etudiantRepository.delete(etudiant);
    }
}