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


    public void modifier(Long id, Permanent updatedPermanent) {
        Permanent permanent = rechercher(id);

        permanent.setNom(updatedPermanent.getNom());
        permanent.setPrenom(updatedPermanent.getPrenom());
        permanent.setUsername(updatedPermanent.getUsername());
        permanent.setGrade(updatedPermanent.getGrade());
        permanent.setMatricule(updatedPermanent.getMatricule());
        permanent.setSpecialite(updatedPermanent.getSpecialite());
        permanent.setArchive(updatedPermanent.isArchive());
        permanentRepository.save(permanent);
    }

    public void supprimer(Long id) {
        Permanent permanent = rechercher(id);
        permanentRepository.delete(permanent);
    }
}
