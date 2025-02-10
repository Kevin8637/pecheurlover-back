package com.pecheur_lover.pecheurlover.daos;

import com.pecheur_lover.pecheurlover.entities.Invoice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class InvoiceDao {
    private final JdbcTemplate jdbcTemplate;

    public InvoiceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Invoice> invoiceRowMapper = (rs, _) -> new Invoice(
            rs.getLong("id_invoice"),
            rs.getLong("id_user"),
            rs.getLong("id_product"),
            rs.getLong("quantity"),
            rs.getLong("total_price"),
            rs.getDate("invoice_date")
    );

    public Invoice findById(Long id_invoice) {
        String sql = "SELECT * FROM invoice WHERE id_invoice = ?";
        return jdbcTemplate.query(sql, invoiceRowMapper, id_invoice)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Facture non trouvée"));
    }

    public List<Invoice> findAll() {
        String sql = "SELECT * FROM invoice";
        return jdbcTemplate.query(sql, invoiceRowMapper);
    }
}
