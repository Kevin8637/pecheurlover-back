package com.pecheur_lover.pecheurlover.controllers;

import com.pecheur_lover.pecheurlover.daos.UserDao;
import com.pecheur_lover.pecheurlover.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Contrôleur REST pour la gestion des utilisateurs
@RestController
@RequestMapping("/users")
public class UserController {
    // Injection du DAO pour accéder aux données utilisateur
    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    // Récupérer la liste de tous les utilisateurs
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userDao.findAll());
    }

    // Récupérer un utilisateur par son email
    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userDao.findByEmail(email));
    }
}
