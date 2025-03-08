package com.coursuasz.l32i.daos.microserviceutilisateur.mapper;

import com.coursuasz.l32i.daos.microserviceutilisateur.dto.EtudiantDTO;
import com.coursuasz.l32i.daos.microserviceutilisateur.dto.LoginDTO;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Etudiant;
import org.mapstruct.Mapper;

@Mapper
public interface EtudiantMapper {
    LoginDTO EtudiantToDTO(Etudiant etudiant);

    Etudiant dTOToEtudiant(EtudiantDTO etudiantDTO);

    LoginDTO EtudiantToLogin(Etudiant etudiant);

    EtudiantDTO loginToEtudiant(LoginDTO loginDTO);
}
