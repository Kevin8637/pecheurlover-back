package com.pecheur_lover.pecheurlover.security;

import com.pecheur_lover.pecheurlover.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Filtre personnalisé pour gérer l'authentification JWT sur chaque requête
@Component
public class JwtFilter extends OncePerRequestFilter {

    // Dépendances pour la validation JWT et le chargement des utilisateurs
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    // Méthode principale de traitement de la requête
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {
            // 1. Extraction du JWT depuis l'en-tête Authorization
            String jwt = parseJwt(request);

            // 2. Validation du token JWT
            if (jwt != null && jwtUtil.validateJwtToken(jwt)) {

                // 3. Récupération de l'email depuis le token
                String username = jwtUtil.getEmailFromToken(jwt);

                // 4. Chargement des détails de l'utilisateur
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 5. Création de l'objet d'authentification Spring Security
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // 6. Ajout des détails de la requête (IP, session, etc.)
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 7. Stockage de l'authentification dans le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.out.println("Cannot set user authentication: " + e);
        }

        // 8. Poursuite de la chaîne de filtres
        chain.doFilter(request, response);
    }

    // Méthode utilitaire pour extraire le JWT de l'en-tête Authorization
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // Retire le préfixe "Bearer "
        }
        return null;
    }
}