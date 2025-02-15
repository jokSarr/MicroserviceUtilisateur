package com.coursuasz.l32i.daos.microserviceutilisateur.service;

import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceAlreadyExistException;
import com.coursuasz.l32i.daos.microserviceutilisateur.exception.ResourceNotFoundException;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Utilisateur;
import com.coursuasz.l32i.daos.microserviceutilisateur.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public void ajouter(Utilisateur utilisateur) {
        Utilisateur existe = utilisateurRepository.findByUsername(utilisateur.getUsername());
        if(existe != null)
            throw new ResourceAlreadyExistException("Le username "+utilisateur.getUsername()+" existe déja");
        try {
            utilisateurRepository.save(utilisateur);
        } catch(Exception exception){
            throw new ResourceNotFoundException("Erreur lors de l'ajout");
        }
    }

    public Utilisateur rechercher(String username){
        try{
            return utilisateurRepository.findByUsername(username);
        }catch(Exception exception){
            throw new ResourceNotFoundException("Utilisateur "+username+" n'existe pas");
        }
    }
}