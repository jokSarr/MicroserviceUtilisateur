package com.coursuasz.l32i.daos.microserviceutilisateur.controller;

import com.coursuasz.l32i.daos.microserviceutilisateur.dto.LoginDTO;
import com.coursuasz.l32i.daos.microserviceutilisateur.dto.UtilisateurDTO;
import com.coursuasz.l32i.daos.microserviceutilisateur.jwt.JwtUtils;
import com.coursuasz.l32i.daos.microserviceutilisateur.mapper.UtilisateurMapper;
import com.coursuasz.l32i.daos.microserviceutilisateur.modele.Utilisateur;
import com.coursuasz.l32i.daos.microserviceutilisateur.service.UtilisateurService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Data
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private UtilisateurMapper utilisateurMapper = Mappers.getMapper(UtilisateurMapper.class);

    @PostMapping(path = "/inscrire")
    public ResponseEntity<?> ajouter (@RequestBody UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = utilisateurMapper.dTOToUtilisateur(utilisateurDTO);
        String password = passwordEncoder.encode("Passer123");
        utilisateur.setPassword(password);
        utilisateurService.ajouter(utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(utilisateurDTO);
    }

    @PostMapping(path = "/connecter")
    public ResponseEntity<?> authentifier(@RequestBody LoginDTO loginDTO) {
        Utilisateur utilisateur = utilisateurMapper.loginToUtilisateur(loginDTO);
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(utilisateur.getUsername(), utilisateur.getPassword()));
            if (authentication.isAuthenticated()) {
                System.out.println(authentication);
                Map<String, Object> authData = new HashMap<>();
                authData.put("token",jwtUtils.generateToken(utilisateur.getUsername()));
                authData.put("type", "Bearer");
                return ResponseEntity.ok(authData);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("erreur sur username ou password");
        }catch (AuthenticationException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("erreur sur username ou password");
        }
    }
}