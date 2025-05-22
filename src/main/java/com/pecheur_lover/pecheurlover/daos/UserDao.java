package com.pecheur_lover.pecheurlover.daos;

import com.pecheur_lover.pecheurlover.entities.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

// DAO pour la gestion des utilisateurs et des opérations d'authentification
@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    // Injection du JdbcTemplate pour les opérations SQL
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mapper pour convertir les résultats de requête SQL en objets User
    private final RowMapper<User> userRowMapper = (rs, _) -> new User(
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("role")
    );

    // Récupère un utilisateur par son email (utilisé par Spring Security)
    public User findByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        return jdbcTemplate.query(sql, userRowMapper, email)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
    }

    // Récupère tous les utilisateurs de la base
    public List<User> findAll(){
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    // Enregistre un nouvel utilisateur avec son rôle
    public boolean save(User user) {
        String sql = "INSERT INTO `user` (email, password, role) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getRole());
        return rowsAffected > 0;
    }

    // Vérifie l'existence d'un email dans la base (pour l'inscription)
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, email) > 0;
    }
}
