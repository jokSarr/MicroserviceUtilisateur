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

    public Etudiant modifier(Long id, Etudiant updatedEtudiant) {
        // Vérifier si l'étudiant existe
        Etudiant existingEtudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étudiant avec ID " + id + " non trouvé"));

        // Mise à jour des champs hérités de Utilisateur
        existingEtudiant.setUsername(updatedEtudiant.getUsername());
        existingEtudiant.setNom(updatedEtudiant.getNom());
        existingEtudiant.setPrenom(updatedEtudiant.getPrenom());
        existingEtudiant.setPassword(updatedEtudiant.getPassword());
        existingEtudiant.setActive(updatedEtudiant.isActive());
        existingEtudiant.setRole(updatedEtudiant.getRole());

        // Mise à jour du champ spécifique à Etudiant
        existingEtudiant.setMatricule(updatedEtudiant.getMatricule());

        // Sauvegarde des modifications
        return etudiantRepository.save(existingEtudiant);
    }

    public void supprimer(Long id) {
        Etudiant etudiant = rechercher(id);
        etudiantRepository.delete(etudiant);
    }
}