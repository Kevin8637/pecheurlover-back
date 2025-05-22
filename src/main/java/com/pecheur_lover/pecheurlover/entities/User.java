package com.pecheur_lover.pecheurlover.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Entité représentant un utilisateur de l'application
public class User {

    // Email de l'utilisateur, obligatoire et validé au format email
    @NotBlank(message = "L'email ne peut pas être vide")
    @Email
    private String email;

    // Mot de passe de l'utilisateur, obligatoire
    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    private String password;

    // Rôle de l'utilisateur (ex : "USER", "ADMIN"), obligatoire
    @NotBlank(message = "Le rôle ne peut pas être vide")
    private String role;

    // Constructeur par défaut (utilisé par les frameworks de sérialisation/désérialisation)
    public User() {
    }

    // Constructeur complet pour initialiser tous les champs
    public User(String email, String password, String role){
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters et setters pour chaque propriété

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
