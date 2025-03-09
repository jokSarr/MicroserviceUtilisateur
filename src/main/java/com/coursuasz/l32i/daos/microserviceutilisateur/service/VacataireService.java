package com.coursuasz.l32i.daos.microserviceutilisateur.service;

import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceAlreadyExistException;
import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceNotFoundException;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Vacataire;
import com.coursuasz.l32i.daos.microserviceutilisateur.repository.VacataireRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class VacataireService {
    @Autowired
    private VacataireRepository vacataireRepository;

    public void ajouter(Vacataire vacataire) {
        vacataireRepository.findByMatricule(vacataire.getMatricule())
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistException("L'id " + vacataire.getId() + " existe déjà");
                });

        try {
            vacataireRepository.save(vacataire);
        } catch (Exception exception) {
            throw new RuntimeException("Erreur lors de l'ajout du vacataire", exception);
        }
    }

    public Vacataire rechercher(Long id){
        try {
            return vacataireRepository.findById(id).get();
        }catch (Exception e){
            throw new ResourceNotFoundException("vacataire avec ID "+id+"n'existe pas ");
        }

    }

    public List<Vacataire> Liste (){
        return vacataireRepository.findAll();
    }


    public Vacataire modifier(Long id, Vacataire updatedVacataire) {
        // Vérifier si le Vacataire existe
        Vacataire existingVacataire = vacataireRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacataire avec ID " + id + " non trouvé"));

        // Mise à jour des champs hérités de Utilisateur
        existingVacataire.setUsername(updatedVacataire.getUsername());
        existingVacataire.setNom(updatedVacataire.getNom());
        existingVacataire.setPrenom(updatedVacataire.getPrenom());
        existingVacataire.setPassword(updatedVacataire.getPassword());
        existingVacataire.setActive(updatedVacataire.isActive());
        existingVacataire.setRoles(updatedVacataire.getRoles());

        // Mise à jour des champs hérités de Enseignant
        existingVacataire.setGrade(updatedVacataire.getGrade());
        existingVacataire.setMatricule(updatedVacataire.getMatricule());
        existingVacataire.setArchive(updatedVacataire.isArchive());

        // Mise à jour du champ spécifique à Vacataire
        existingVacataire.setNiveau(updatedVacataire.getNiveau());

        // Sauvegarde des modifications
        return vacataireRepository.save(existingVacataire);
    }

    public void supprimer(Long id) {
        Vacataire vacataire = rechercher(id);
        vacataireRepository.delete(vacataire);
    }
}