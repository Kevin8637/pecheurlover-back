package com.pecheur_lover.pecheurlover.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {

    // Injection des propriétés depuis application.properties
    @Value("${jwt.secret}")
    private String jwtSecret; // Clé secrète pour signer les JWT

    @Value("${jwt.expiration}")
    private int jwtExpirationMs; // Durée de validité des tokens en millisecondes

    private SecretKey key; // Clé de chiffrement générée

    // Méthode d'initialisation après construction du bean
    @PostConstruct
    public void init() {
        // Génération de la clé HMAC à partir du secret
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // Génère un token JWT pour un email donné
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Définit le sujet (identifiant utilisateur)
                .setIssuedAt(new Date()) // Date de création
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Date d'expiration
                .signWith(key, SignatureAlgorithm.HS256) // Signature avec algorithme HS256
                .compact(); // Génère le token sous forme de chaîne
    }

    // Extrait l'email d'un token JWT
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build() // Configure la clé de vérification
                .parseClaimsJws(token) // Parse le token
                .getBody()
                .getSubject(); // Récupère le sujet (email)
    }

    // Valide l'intégrité et la validité d'un token JWT
    public boolean validateJwtToken(String token) {
        try {
            // Tentative de parsing qui valide automatiquement la signature et l'expiration
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        // Gestion des différentes erreurs possibles
        catch (SecurityException e) {
            throw new SecurityException("Signature JWT invalide : " + e.getMessage());
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("Token JWT mal formé : " + e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "Token JWT expiré : " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("Format de token non supporté : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Claim JWT vide : " + e.getMessage());
        }
    }
}
