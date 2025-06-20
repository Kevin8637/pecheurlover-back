package com.pecheur_lover.pecheurlover.daos;

import com.pecheur_lover.pecheurlover.entities.Product;
import com.pecheur_lover.pecheurlover.exceptions.ResourceNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
            rs.getLong("id_product"),
            rs.getString("name"),
            rs.getString("country"),
            rs.getString("description"),
            rs.getString("imageUrl"),
            rs.getString("bait"),
            rs.getString("cook_tips"),
            rs.getString("vegetables_tips"),
            rs.getDouble("price"),
            rs.getLong("stock")
    );

    public Product findById(Long id_product) {
        String sql = "SELECT * FROM product WHERE id_product = ?";
        return jdbcTemplate.query(sql, productRowMapper, id_product)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Produit avec l'ID : " + id_product + " n'existe pas"));
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public Product save(Product product) {
        String sql = "INSERT INTO product (name, country, description, imageUrl, bait, cook_tips, vegetables_tips, price, stock) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getCountry(), product.getDescription(), product.getImageUrl(), product.getBait(), product.getCook_tips(), product.getVegetables_tips(), product.getPrice(), product.getStock());

        Long id_product = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        product.setId_product(id_product);
        return product;
    }

    public Product update(Long id_product, Product product) {
        if (!productExists(id_product)) {
            throw new ResourceNotFoundException("Produit avec l'ID : " + id_product + " n'existe pas");
        }

        String sql = "UPDATE product SET name = ?, price = ?, country = ?, description = ?, imageUrl = ?, bait = ?, cook_tips = ?, vegetables_tips = ?, stock = ? WHERE id_product = ?";
        int rowsAffected = jdbcTemplate.update(sql,
                product.getName(), product.getPrice(), product.getCountry(),
                product.getDescription(), product.getImageUrl(), product.getBait(),
                product.getCook_tips(), product.getVegetables_tips(), product.getStock(),
                id_product
        );

        if (rowsAffected <= 0) {
            throw new ResourceNotFoundException("Échec de la mise à jour du produit avec l'ID : " + id_product);
        }

        return findById(id_product);
    }

    public boolean delete(Long id_product) {
        String checkSql = "SELECT COUNT(*) FROM product WHERE id_product = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, id_product);

        if (count == null || count == 0) {
            throw new ResourceNotFoundException("Produit avec l'ID : " + id_product + " n'existe pas");
        }

        String sql = "DELETE FROM product WHERE id_product = ?";
        int rowsAffected = jdbcTemplate.update(sql, id_product);
        return rowsAffected > 0;
    }

    public boolean productExists(Long id_product) {
        String sql = "SELECT COUNT(*) FROM product WHERE id_product = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id_product) > 0;
    }

    public Integer getStockById(Long id_product) {
        String sql = "SELECT stock FROM product WHERE id_product = ?";
        Integer stock = jdbcTemplate.queryForObject(sql, Integer.class, id_product);

        if (stock == null) {
            throw new ResourceNotFoundException("Stock introuvable pour le produit ID : " + id_product);
        }

        return stock;
    }

    public boolean updateStock(Long id_product, Integer newStock) {
        if (!productExists(id_product)) {
            return false;
        }

        String sql = "UPDATE product SET stock = ? WHERE id_product = ?";
        int updated = jdbcTemplate.update(sql, newStock, id_product);
        return updated > 0;
    }
}
