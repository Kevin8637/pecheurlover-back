package com.pecheur_lover.pecheurlover.daos;

import com.pecheur_lover.pecheurlover.entities.Orders;
import com.pecheur_lover.pecheurlover.exceptions.ResourceNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// DAO pour la gestion des commandes (Orders) avec jointures entre les tables
@Repository
public class OrdersDao {

    private final JdbcTemplate jdbcTemplate;

    public OrdersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mapper personnalisé pour convertir les résultats SQL en objets Orders
    private final RowMapper<Orders> orderRowMapper = (rs, rowNum) -> new Orders(
            rs.getLong("id_product"),
            rs.getLong("id_invoice"),
            rs.getLong("quantity"),
            rs.getDouble("price"),
            rs.getString("product_name") != null ? rs.getString("product_name") : "Nom inconnu", // Valeur par défaut
            rs.getString("product_image") != null ? rs.getString("product_image") : "https://via.placeholder.com/100", // Image par défaut
            rs.getDate("invoice_date"),
            rs.getDouble("total_price")
    );

    // Récupère les commandes d'une facture spécifique avec jointure sur product et invoice
    public List<Orders> findOrdersByInvoiceId(int id_invoice) {
        String sql = """
            SELECT o.id_invoice, o.id_product, o.quantity, o.price,
                   p.name AS product_name, p.imageUrl AS product_image,
                   i.invoice_date, i.total_price
            FROM orders o
            JOIN invoice i ON o.id_invoice = i.id_invoice
            JOIN product p ON o.id_product = p.id_product
            WHERE o.id_invoice = ?
        """;
        return jdbcTemplate.query(sql, new Object[]{id_invoice}, orderRowMapper);
    }

    // Récupère l'historique des commandes d'un utilisateur (email) avec jointures
    public List<Orders> findOrdersByEmail(String email) {
        String sql = """
            SELECT o.id_invoice, o.id_product, o.quantity, o.price, 
                   p.name AS product_name, p.imageUrl AS product_image,
                   i.invoice_date, i.total_price
            FROM orders o
            JOIN invoice i ON o.id_invoice = i.id_invoice
            JOIN product p ON o.id_product = p.id_product
            WHERE LOWER(i.email) = LOWER(?)
        """;
        return jdbcTemplate.query(sql, new Object[]{email}, orderRowMapper);
    }

    // Méthodes CRUD standard avec gestion des exceptions
    public Orders findById(Long id_invoice) {
        String sql = "SELECT * FROM orders WHERE id_invoice = ?";
        return jdbcTemplate.query(sql, orderRowMapper, id_invoice)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée"));
    }

    // Récupère toutes les commandes avec détails complets (produit + facture)
    public List<Orders> findAll() {
        String sql = """
            SELECT o.id_invoice, o.id_product, o.quantity, o.price,
                   i.email,
                   p.name AS product_name, p.imageUrl AS product_image,
                   i.invoice_date, i.total_price
            FROM orders o
            JOIN invoice i ON o.id_invoice = i.id_invoice
            JOIN product p ON o.id_product = p.id_product
        """;
        return jdbcTemplate.query(sql, new Object[]{}, orderRowMapper);
    }

    // Crée une nouvelle commande et récupère l'ID généré
    public Orders save(Orders order) {
        String sql = "INSERT INTO orders (id_product, quantity, price, id_invoice) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, order.getId_product(), order.getQuantity(), order.getPrice(), order.getId_invoice());

        String sqlGetId = "SELECT LAST_INSERT_ID()";
        Long id_invoice = jdbcTemplate.queryForObject(sqlGetId, Long.class);

        order.setId_invoice(id_invoice);
        return order;
    }

    // Met à jour une commande existante
    public Orders update(Long id_invoice, Orders order) {
        if (!orderExists(id_invoice)) {
            throw new ResourceNotFoundException("Commande avec l'ID : " + id_invoice + " n'existe pas");
        }

        String sql = "UPDATE orders SET id_product = ?, quantity = ?, price = ? WHERE id_invoice = ?";
        int rowsAffected = jdbcTemplate.update(sql, order.getId_product(), order.getQuantity(), order.getPrice(), id_invoice);

        if (rowsAffected <= 0) {
            throw new ResourceNotFoundException("Échec de la mise à jour de la commande avec l'ID : " + id_invoice);
        }
        return order;
    }

    // Supprime une commande et retourne le statut de l'opération
    public boolean delete(Long id_invoice) {
        String sql = "DELETE FROM orders WHERE id_invoice = ?";
        int rowsAffected = jdbcTemplate.update(sql, id_invoice);
        return rowsAffected > 0;
    }

    // Vérifie l'existence d'une commande
    public boolean orderExists(Long id_invoice) {
        String sql = "SELECT COUNT(*) FROM orders WHERE id_invoice = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id_invoice) > 0;
    }
}
