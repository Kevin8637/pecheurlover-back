package com.pecheur_lover.pecheurlover.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

// Implémentation personnalisée de UserDetails pour intégrer l'entité User avec Spring Security
public class CustomUserDetails implements UserDetails {
    private final User user; // Référence à l'entité User métier

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Convertit le rôle de l'utilisateur en autorité Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> "ROLE_" + user.getRole()); // Convention de nommage Spring pour les rôles
    }

    // Méthodes d'accès aux informations de sécurité
    @Override
    public String getPassword() {
        return user.getPassword(); // Mot de passe hashé de l'utilisateur
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Utilisation de l'email comme identifiant unique
    }

    // Méthodes de statut du compte (toujours actif dans cette implémentation)
    @Override
    public boolean isAccountNonExpired() {
        return true; // Le compte n'expire jamais
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Le compte n'est jamais verrouillé
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Les identifiants n'expirent jamais
    }

    @Override
    public boolean isEnabled() {
        return true; // Le compte est toujours activé
    }
}
