package com.pecheur_lover.pecheurlover.daos;

import com.pecheur_lover.pecheurlover.entities.Orders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

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
}
