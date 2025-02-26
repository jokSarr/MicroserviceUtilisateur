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

    public Enseignant rechercher(Long id) {
        return enseignantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant avec ID " + id + " introuvable"));
    }

    public List<Enseignant> lister() {
        return enseignantRepository.findAll();
    }

    public void modifier(Long id, Enseignant updatedEnseignant) {
        Enseignant enseignant = rechercher(id);
        enseignant.setNom(updatedEnseignant.getNom());
        enseignant.setPrenom(updatedEnseignant.getPrenom());
        enseignant.setUsername(updatedEnseignant.getUsername());
        enseignant.setGrade(updatedEnseignant.getGrade());
        enseignantRepository.save(enseignant);
    }

    public void supprimer(Long id) {
        Enseignant enseignant = rechercher(id);
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
