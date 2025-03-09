package com.coursuasz.l32i.daos.microserviceutilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.*;
import com.coursuasz.l32i.daos.microserviceutilisateur.service.UtilisateurService;

import java.util.Date;

@SpringBootApplication
public class MicroserviceUtilisateurApplication implements CommandLineRunner {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceUtilisateurApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Encodage du mot de passe par défaut
        String password = passwordEncoder.encode("Passer123");

  /*      // Ajouter des rôles
        Role rolePermanent = utilisateurService.ajouterRole(new Role("PERMANENT"));
        Role roleVacataire = utilisateurService.ajouterRole(new Role("VACATAIRE"));
        Role roleChefDepartement = utilisateurService.ajouterRole(new Role("CHEF-DEPARTEMENT"));

        // Création d'un utilisateur Permanent
        Permanent permanent = new Permanent();
        permanent.setNom("DIAGNE");
        permanent.setPrenom("Serigne");
        permanent.setUsername("sdiagne@uasz.sn");
        permanent.setPassword(password);
        permanent.setDateCreation(new Date());
        permanent.setActive(true);
        permanent.setSpecialite("Base de données");
        permanent.setMatricule("SD2024");
        permanent.setGrade("Professeur");

        utilisateurService.ajouter(permanent);
        utilisateurService.ajouterRoleAUtilisateur("sdiagne@uasz.sn", "PERMANENT");

        // Création d'un autre utilisateur Permanent
        Permanent permanent2 = new Permanent();
        permanent2.setNom("DIOP");
        permanent2.setPrenom("Ibrahima");
        permanent2.setUsername("idiop@uasz.sn");
        permanent2.setPassword(password);
        permanent2.setDateCreation(new Date());
        permanent2.setActive(true);
        permanent2.setSpecialite("Web Sémantique");
        permanent2.setMatricule("ID2024");
        permanent2.setGrade("Professeur");

        utilisateurService.ajouter(permanent2);
        utilisateurService.ajouterRoleAUtilisateur("idiop@uasz.sn", "PERMANENT");

        // Création d'un utilisateur Vacataire
        Vacataire vacataire = new Vacataire();
        vacataire.setNom("MALACK");
        vacataire.setPrenom("Camir");
        vacataire.setUsername("cmalack@uasz.sn");
        vacataire.setPassword(password);
        vacataire.setDateCreation(new Date());
        vacataire.setActive(true);
        vacataire.setNiveau("Doctorant");

        utilisateurService.ajouter(vacataire);
        utilisateurService.ajouterRoleAUtilisateur("cmalack@uasz.sn", "VACATAIRE");

        // Création d'un utilisateur Chef de Département
        Permanent chefDepartement = new Permanent();
        chefDepartement.setNom("DRAME");
        chefDepartement.setPrenom("Yahya");
        chefDepartement.setUsername("ydrame@uasz.sn");
        chefDepartement.setPassword(password);
        chefDepartement.setDateCreation(new Date());
        chefDepartement.setActive(true);
        chefDepartement.setSpecialite("Web Sémantique");
        chefDepartement.setMatricule("ID012024");
        chefDepartement.setGrade("Professeur");

        utilisateurService.ajouter(chefDepartement);
        utilisateurService.ajouterRoleAUtilisateur("ydrame@uasz.sn", "CHEF-DEPARTEMENT");*/
    }
}