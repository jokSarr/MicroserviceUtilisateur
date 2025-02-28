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


    public void modifier(Long id, Vacataire updatedVacataire) {
        Vacataire vacataire = vacataireRepository.findById(id).get();

        vacataire.setNom(updatedVacataire.getNom());
        vacataire.setPrenom(updatedVacataire.getPrenom());
        vacataire.setActive(updatedVacataire.isActive());
        vacataire.setArchive(updatedVacataire.isArchive());
        vacataireRepository.save(vacataire);
    }

    public void supprimer(Long id) {
        Vacataire vacataire = rechercher(id);
        vacataireRepository.delete(vacataire);
    }
}