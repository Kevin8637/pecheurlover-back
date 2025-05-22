package com.pecheur_lover.pecheurlover.controllers;

import com.pecheur_lover.pecheurlover.daos.UserDao;
import com.pecheur_lover.pecheurlover.entities.User;
import com.pecheur_lover.pecheurlover.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// Contrôleur REST pour la gestion de l'authentification (register/login)
@RestController
@RequestMapping("/auth")
public class AuthController {
    // Injection des dépendances Spring Security et utilitaires
    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtils;

    // Constructeur pour l'injection des dépendances
    public AuthController(AuthenticationManager authenticationManager, UserDao userDao, PasswordEncoder encoder, JwtUtil jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDao = userDao;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    // Endpoint d'enregistrement d'un nouvel utilisateur
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Vérification de l'existence de l'email
        boolean alreadyExists = userDao.existsByEmail(user.getEmail());
        if (alreadyExists) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Création et sauvegarde du nouvel utilisateur avec mot de passe hashé
        User newUser = new User(
                user.getEmail(),
                encoder.encode(user.getPassword()), // Hashage du mot de passe
                "USER" // Attribution du rôle par défaut
        );

        boolean isUserSaved = userDao.save(newUser);
        return isUserSaved ?
                ResponseEntity.ok("User registered successfully!") :
                ResponseEntity.badRequest().body("Error: User registration failed!");
    }

    // Endpoint de connexion et génération du JWT
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user) {
        // Authentification via Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        // Génération du token JWT après authentification réussie
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails.getUsername());

        // Renvoi du token dans la réponse
        return ResponseEntity.ok().body(Map.of("token", token));
    }
}
