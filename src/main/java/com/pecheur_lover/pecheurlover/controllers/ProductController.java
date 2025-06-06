package com.pecheur_lover.pecheurlover.controllers;

import com.pecheur_lover.pecheurlover.daos.ProductDao;
import com.pecheur_lover.pecheurlover.entities.Product;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// Contrôleur REST pour la gestion des produits
@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000") // Autorise les requêtes du front local
public class ProductController {
    // Injection du DAO produit et du JdbcTemplate pour les requêtes SQL directes
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    public ProductController(ProductDao productDao, JdbcTemplate jdbcTemplate) {
        this.productDao = productDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Récupérer tous les produits
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productDao.findAll());
    }

    // Récupérer un produit par son identifiant
    @GetMapping("/{id_product}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id_product){
        return ResponseEntity.ok(productDao.findById(id_product));
    }

    // Récupérer le stock d'un produit spécifique (requête SQL directe)
    @GetMapping("/{id_product}/stock")
    public ResponseEntity<Map<String, Object>> getStock(@PathVariable Long id_product) {
        String sql = "SELECT stock FROM product WHERE id_product = ?";
        try {
            Integer stock = jdbcTemplate.queryForObject(sql, Integer.class, id_product);
            if (stock == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Produit introuvable"));
            }
            return ResponseEntity.ok(Map.of("stock", stock));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erreur serveur"));
        }
    }

    // Créer un nouveau produit
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product){
        Product createdProduct = productDao.save(product);
        return ResponseEntity.ok(createdProduct);
    }

    // Mettre à jour le stock d'un produit (requête SQL directe)
    @PutMapping("/{id_product}/update-stock")
    public ResponseEntity<Map<String, String>> updateStock(@PathVariable Long id_product, @RequestBody Map<String, Integer> body) {
        Integer newStock = body.get("newStock");

        if (newStock < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Le stock ne peut pas être négatif"));
        }

        String sql = "UPDATE product SET stock = ? WHERE id_product = ?";
        int updated = jdbcTemplate.update(sql, newStock, id_product);

        if (updated > 0) {
            return ResponseEntity.ok(Map.of("message", "Stock mis à jour"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Produit non trouvé"));
        }
    }

    // Mettre à jour les informations d'un produit
    @PutMapping("/{id_product}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id_product, @RequestBody Product product) {
        Product updatedProduct = productDao.update(id_product, product);
        return ResponseEntity.ok(updatedProduct);
    }

    // Supprimer un produit
    @DeleteMapping("/{id_product}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id_product) {
        if(productDao.delete(id_product)){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
