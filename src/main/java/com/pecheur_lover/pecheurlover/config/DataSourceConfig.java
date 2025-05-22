package com.pecheur_lover.pecheurlover.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

// Classe de configuration pour la source de données (connexion à la base de données)
@Configuration
public class DataSourceConfig {

    // Injection des propriétés de configuration de la base de données depuis application.properties
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    // Déclaration du bean DataSource pour gérer la connexion à la base de données
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource(
                url,
                username,
                password
        );
        driver.setDriverClassName(driverClassName);
        return driver;
    }

    // Déclaration du bean JdbcTemplate pour faciliter les opérations SQL avec la base de données
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
