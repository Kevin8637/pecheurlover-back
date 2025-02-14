package com.pecheur_lover.pecheurlover.daos;

import com.pecheur_lover.pecheurlover.entities.Orders;
import com.pecheur_lover.pecheurlover.exceptions.ResourceNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrdersDao {

    private final JdbcTemplate jdbcTemplate;

    public OrdersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Orders> orderRowMapper = (rs, rowNum) -> new Orders(
            rs.getLong("id_product"),
            rs.getLong("id_invoice"),
            rs.getLong("quantity"),
            rs.getDouble("price"),
            rs.getString("product_name") != null ? rs.getString("product_name") : "Nom inconnu",
            rs.getString("product_image") != null ? rs.getString("product_image") : "https://via.placeholder.com/100",
            rs.getDate("invoice_date"),
            rs.getDouble("total_price")
    );


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

        List<Orders> orders = jdbcTemplate.query(sql, new Object[]{id_invoice}, orderRowMapper);

        return orders;
    }



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

    public Orders findById(Long id_invoice) {
        String sql = "SELECT * FROM orders WHERE id_invoice = ?";
        return jdbcTemplate.query(sql, orderRowMapper, id_invoice)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée"));
    }

    public List<Orders> findAll() {
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, orderRowMapper);
    }

    public Orders save(Orders order) {
        String sql = "INSERT INTO orders (id_product, quantity, price, id_invoice) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, order.getId_product(), order.getQuantity(), order.getPrice(), order.getId_invoice());

        String sqlGetId = "SELECT LAST_INSERT_ID()";
        Long id_invoice = jdbcTemplate.queryForObject(sqlGetId, Long.class);

        order.setId_invoice(id_invoice);
        return order;
    }

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

    public boolean delete(Long id_invoice) {
        String sql = "DELETE FROM orders WHERE id_invoice = ?";
        int rowsAffected = jdbcTemplate.update(sql, id_invoice);
        return rowsAffected > 0;
    }

    public boolean orderExists(Long id_invoice) {
        String sql = "SELECT COUNT(*) FROM orders WHERE id_invoice = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id_invoice) > 0;
    }
}
