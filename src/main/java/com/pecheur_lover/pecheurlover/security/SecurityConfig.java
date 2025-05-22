package com.pecheur_lover.pecheurlover.security;

import com.pecheur_lover.pecheurlover.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Injection des dépendances pour le filtre JWT et le service utilisateur personnalisé
    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtFilter jwtFilter, CustomUserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    // Configuration principale de la sécurité HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Désactive CSRF (inutile pour les API stateless)
                .cors(Customizer.withDefaults()) // Active la configuration CORS personnalisée
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Mode sans session
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/auth/**", "/test/all").permitAll() // Accès public
                                .requestMatchers("/user/**").hasRole("USER") // Nécessite le rôle USER
                                .requestMatchers("/admin/**").hasRole("ADMIN") // Nécessite le rôle ADMIN
                                .anyRequest().authenticated() // Toutes autres requêtes nécessitent une authentification
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Ajout du filtre JWT
        return http.build();
    }

    // Configuration du fournisseur d'authentification
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Service personnalisé pour charger les utilisateurs
        provider.setPasswordEncoder(new BCryptPasswordEncoder()); // Encodeur de mot de passe
        return provider;
    }

    // Exposition du gestionnaire d'authentification
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configuration de l'encodeur de mots de passe
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Utilisation de BCrypt pour le hachage
    }

    // Configuration CORS pour les requêtes cross-origin
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:3000")); // Autorise le front-end React
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")); // Méthodes autorisées
        configuration.setAllowCredentials(true); // Autorise les cookies/credentials
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type")); // En-têtes autorisés
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Applique à toutes les routes
        return source;
    }
}
