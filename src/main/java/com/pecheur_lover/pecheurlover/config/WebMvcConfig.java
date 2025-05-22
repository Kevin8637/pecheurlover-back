package com.pecheur_lover.pecheurlover.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Classe de configuration pour le support Web MVC et la gestion du CORS
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    // Configuration du CORS pour autoriser les requêtes cross-origin depuis le front-end React
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Autorise toutes les routes de l'API
                .allowedOrigins("http://localhost:3000") // Autorise uniquement le front-end local
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes HTTP autorisées
                .allowedHeaders("*") // Autorise tous les headers
                .allowCredentials(true); // Autorise l'envoi de cookies et d'identifiants
    }
}
