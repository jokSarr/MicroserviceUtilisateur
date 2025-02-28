package com.coursuasz.l32i.daos.microserviceutilisateur.service;

import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceAlreadyExistException;
import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceNotFoundException;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Permanent;
import com.coursuasz.l32i.daos.microserviceutilisateur.repository.PermanentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PermanentService {
    @Autowired
    private PermanentRepository permanentRepository;

    public void ajouter(Permanent permanent) {
        permanentRepository.findByMatricule(permanent.getMatricule())
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistException("Le matricule " + permanent.getMatricule() + " existe déjà");
                });

        try {
            permanentRepository.save(permanent);
        } catch (Exception exception) {
            throw new RuntimeException("Erreur lors de l'ajout du permanent", exception);
        }
    }

    public Permanent rechercher(Long id){
        try {
            return permanentRepository.findById(id).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("Permanent avec ID "+id+"n'existe pas ");
        }

    }

    public List<Permanent> Liste (){
        return permanentRepository.findAll();
    }


    public Permanent modifier(Long id, Permanent updatedPermanent) {
        // Vérifier si le Permanent existe
        Permanent existingPermanent = permanentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permanent avec ID " + id + " non trouvé"));

        // Mise à jour des champs hérités de Utilisateur
        existingPermanent.setUsername(updatedPermanent.getUsername());
        existingPermanent.setNom(updatedPermanent.getNom());
        existingPermanent.setPrenom(updatedPermanent.getPrenom());
        existingPermanent.setPassword(updatedPermanent.getPassword());
        existingPermanent.setActive(updatedPermanent.isActive());
        existingPermanent.setRole(updatedPermanent.getRole());

        // Mise à jour des champs hérités de Enseignant
        existingPermanent.setGrade(updatedPermanent.getGrade());
        existingPermanent.setMatricule(updatedPermanent.getMatricule());
        existingPermanent.setArchive(updatedPermanent.isArchive());

        // Mise à jour du champ spécifique à Permanent
        existingPermanent.setSpecialite(updatedPermanent.getSpecialite());

        // Sauvegarde des modifications
        return permanentRepository.save(existingPermanent);
    }

    public void supprimer(Long id) {
        Permanent permanent = rechercher(id);
        permanentRepository.delete(permanent);
    }
}
