package com.pecheur_lover.pecheurlover.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class User {

    @NotBlank (message = "L'email ne peut pas être vide")
    @Email
    private String email;
    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    private String password;
    @NotBlank(message = "Le rôle ne peut pas être vide")
    private String role;

    public User() {
    }

    public User(String email, String password, String role){
        this.email = email;
        this.password = password;
        this.role = role;
    }

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
