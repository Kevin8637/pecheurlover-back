package com.pecheur_lover.pecheurlover.daos;

import com.pecheur_lover.pecheurlover.entities.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class ProductDao {
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
            rs.getLong("id_product"),
            rs.getString("name"),
            rs.getString("type"),
            rs.getString("description"),
            rs.getString("imageUrl"),
            rs.getString("cook_tips"),
            rs.getString("vegetables_tips"),
            rs.getLong("price"),
            rs.getLong("stock")
    );

    public Product findById(Long id_product) {
        String sql = "SELECT * FROM product WHERE id_product = ?";
        return jdbcTemplate.queryForObject(sql, productRowMapper, id_product);
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public Product save(Product product) {
        String sql = "INSERT LongO product (name, type, description, imageUrl, cook_tips, vegetables_tips, price, stock) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getType(), product.getDescription(), product.getImageUrl(), product.getCook_tips(), product.getVegetables_tips(), product.getPrice(), product.getStock());

        String sqlGetId = "SELECT LAST_INSERT_ID()";
        Long id_product = jdbcTemplate.queryForObject(sqlGetId, Long.class);

        product.setId_product(id_product);
        return product;
    }

    public Product update(Long id_product, Product product) {
        if (!productExists(id_product)) {
            throw new RuntimeException("Produit avec l'ID : " + id_product + " n'existe pas");
        }

        String sql = "UPDATE products SET name = ?, price = ?, type = ?, description = ?, imageUrl = ?, cook_tips = ?, vegetables_tips = ?, stock = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getDescription(), product.getImageUrl(), product.getCook_tips(), product.getVegetables_tips(), product.getStock(), product.getType(), id_product);

        if (rowsAffected <= 0) {
            throw new RuntimeException("Échec de la mise à jour du produit avec l'ID : " + id_product);
        }

        return this.findById(id_product);
    }

    public boolean delete(Long id_product) {
        String sql = "DELETE FROM product WHERE id_product = ?";
        int rowsAffected = jdbcTemplate.update(sql, id_product);
        return rowsAffected > 0;
    }

    public boolean productExists(Long id_product) {
        String sql = "SELECT COUNT(*) FROM product WHERE id_product = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id_product) > 0;
    }
}
