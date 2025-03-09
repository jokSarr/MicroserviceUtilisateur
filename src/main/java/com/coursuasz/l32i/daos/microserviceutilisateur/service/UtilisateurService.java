package com.coursuasz.l32i.daos.microserviceutilisateur.service;

import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceAlreadyExistException;
import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceNotFoundException;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Role;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Utilisateur;
import com.coursuasz.l32i.daos.microserviceutilisateur.repository.RoleRepository;
import com.coursuasz.l32i.daos.microserviceutilisateur.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void ajouter(Utilisateur utilisateur) {
        Utilisateur existe = utilisateurRepository.findByUsername(utilisateur.getUsername());
        if (existe != null) {
            throw new ResourceAlreadyExistException("Le username " + utilisateur.getUsername() + " existe déjà");
        }
        try {
            utilisateurRepository.save(utilisateur);
        } catch (Exception exception) {
            throw new ResourceNotFoundException("Erreur lors de l'ajout");
        }
    }

    public Role ajouterRole(Role role) {
        return roleRepository.save(role);
    }

    public void ajouterRoleAUtilisateur(String username, String roleName) {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username);
        Role role = roleRepository.findByRole(roleName);
        utilisateur.getRoles().add(role);
    }

    public Utilisateur rechercher(String username){
        try{
            return utilisateurRepository.findByUsername(username);
        }catch(Exception exception){
            throw new ResourceNotFoundException("Utilisateur "+username+" n'existe pas");
        }
    }


    public List<Utilisateur> lister() {
        return utilisateurRepository.findAll();
    }
}