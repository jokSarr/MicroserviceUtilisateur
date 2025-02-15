package com.coursuasz.l32i.daos.microserviceutilisateur.mapper;

import com.coursuasz.l32i.daos.microserviceutilisateur.dto.LoginDTO;
import com.coursuasz.l32i.daos.microserviceutilisateur.dto.UtilisateurDTO;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Utilisateur;
import org.mapstruct.Mapper;

@Mapper
public interface UtilisateurMapper {
    LoginDTO UtilisateurToDTO(Utilisateur utilisateur);

    Utilisateur dTOToUtilisateur(UtilisateurDTO utilisateurDTO);

    LoginDTO UtilisateurToLogin(Utilisateur utilisateur);

    Utilisateur loginToUtilisateur(LoginDTO loginDTO);
}