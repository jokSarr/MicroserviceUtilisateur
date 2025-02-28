package com.coursuasz.l32i.daos.microserviceutilisateur.service;

import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceAlreadyExistException;
import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceNotFoundException;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Enseignant;
import com.coursuasz.l32i.daos.microserviceutilisateur.repository.EnseignantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;

    public void ajouter(Enseignant enseignant) {
        Enseignant existe = enseignantRepository.findByUsername(enseignant.getUsername());
        if (existe != null) {
            throw new ResourceAlreadyExistException("Le username " + enseignant.getUsername() + " existe déjà");
        }
        try {
            enseignantRepository.save(enseignant);
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Erreur lors de l'ajout");
        }
    }

    public Enseignant rechercherParId(Long id) {
        System.out.println("Recherche de l'enseignant avec ID : " + id);
        Enseignant enseignant = enseignantRepository.findById(id).orElse(null);
        System.out.println("Résultat de la recherche : " + (enseignant != null ? enseignant.toString() : "Aucun enseignant trouvé"));
        return enseignant;
    }

    public Enseignant modifier(Long id, Enseignant updatedEnseignant) {
        // Vérifier si l'enseignant existe
        Enseignant existingEnseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant avec ID " + id + " non trouvé"));

        // Mise à jour des champs hérités de Utilisateur
        existingEnseignant.setUsername(updatedEnseignant.getUsername());
        existingEnseignant.setNom(updatedEnseignant.getNom());
        existingEnseignant.setPrenom(updatedEnseignant.getPrenom());
        existingEnseignant.setPassword(updatedEnseignant.getPassword());
        existingEnseignant.setActive(updatedEnseignant.isActive());
        existingEnseignant.setRole(updatedEnseignant.getRole());

        // Mise à jour des champs spécifiques à Enseignant
        existingEnseignant.setGrade(updatedEnseignant.getGrade());
        existingEnseignant.setMatricule(updatedEnseignant.getMatricule());
        existingEnseignant.setArchive(updatedEnseignant.isArchive());

        // Sauvegarde des modifications
        return enseignantRepository.save(existingEnseignant);
    }

    public List<Enseignant> lister() {
        return enseignantRepository.findAll();
    }


    public void supprimer(Long id) {
        Enseignant enseignant = rechercherParId(id);
        enseignantRepository.delete(enseignant);
    }

    public void activer(Long id){
        Enseignant enseignant = enseignantRepository.findById(id).get();
        if (enseignant.isActive()==true){
            enseignant.setActive(false);
        }else {
            enseignant.setActive(true);
        }
        enseignantRepository.save(enseignant);
    }

    public void archiver(Long id){
        Enseignant enseignant = enseignantRepository.findById(id).get();
        if (enseignant.isArchive()==true){
            enseignant.setArchive(false);
        }else {
            enseignant.setArchive(true);
        }
        enseignantRepository.save(enseignant);
    }
}
