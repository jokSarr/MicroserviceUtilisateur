package com.coursuasz.l32i.daos.microserviceutilisateur.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${app.secret-key}")
    private String secretKey;

    @Value("${app.expiration-time}")
    private Long expirationTime;

    // Clé secrète sécurisée
    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Map<String, Object> claims, String subject, String role, Long id) {
        Date expiryDate = Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + expirationTime));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .claim("role", role)
                .claim("id", id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Utilisez la clé sécurisée
                .compact();
    }

    public String generateToken(String username, String role, Long id) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, role, id);
    }

    public String extractUsername(String bearerToken) {
        return extractClaimBody(bearerToken, Claims::getSubject);
    }

    public <T> T extractClaimBody(String bearerToken, Function<Claims, T> claimsResolver) {
        Jws<Claims> jwsClaims = extractClaims(bearerToken);
        return claimsResolver.apply(jwsClaims.getBody());
    }

    private Jws<Claims> extractClaims(String bearerToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Utilisez la clé sécurisée
                .build()
                .parseClaimsJws(bearerToken);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String userName = extractUsername(token);
            return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (Exception e) {
            System.err.println("Erreur de validation du token : " + e.getMessage());
            return false;
        }
    }

    public Date extractExpiry(String bearerToken) {
        return extractClaimBody(bearerToken, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String bearerToken) {
        return extractExpiry(bearerToken).before(new Date());
    }
}