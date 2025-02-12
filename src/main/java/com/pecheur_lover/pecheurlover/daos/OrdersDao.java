package com.pecheur_lover.pecheurlover.daos;

import com.pecheur_lover.pecheurlover.entities.Orders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrdersDao {

    private final JdbcTemplate jdbcTemplate;

    public OrdersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Orders> orderRowMapper = (rs, _) -> new Orders(
            rs.getLong("id_order"),
            rs.getLong("id_invoice"),
            rs.getString("email"),
            rs.getLong("total_price")
    );

    public Orders findById(Long id_order) {
        String sql = "SELECT * FROM orders WHERE id_order = ?";
        return jdbcTemplate.query(sql, orderRowMapper, id_order)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
    }

    public List<Orders> findAll() {
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, orderRowMapper);
    }

    public Orders save(Orders order) {
        String sql = "INSERT INTO orders (id_invoice, email, total_price) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, order.getId_invoice(), order.getEmail(), order.getTotal_price());

        String sqlGetId = "SELECT LAST_INSERT_ID()";
        Long id_order = jdbcTemplate.queryForObject(sqlGetId, Long.class);

        order.setId_order(id_order);
        return order;
    }

    public Orders update(Long id_order, Orders order) {
        if (!orderExists(id_order)) {
            throw new RuntimeException("Commande avec l'ID : " + id_order + " n'existe pas");
        }

        String sql = "UPDATE orders SET id_invoice = ?, email = ?, total_price = ? WHERE id_order = ?";
        int rowsAffected = jdbcTemplate.update(sql, order.getId_invoice(), order.getEmail(), order.getTotal_price(), id_order);

        if (rowsAffected <= 0) {
            throw new RuntimeException("Échec de la mise à jour de la commande avec l'ID : " + id_order);
        }
        return order;
    }

    public boolean delete(Long id_order) {
        String sql = "DELETE FROM orders WHERE id_order = ?";
        int rowsAffected = jdbcTemplate.update(sql, id_order);
        return rowsAffected > 0;
    }

    public boolean orderExists(Long id_order) {
        String sql = "SELECT COUNT(*) FROM orders WHERE id_order = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id_order) > 0;
    }
}
