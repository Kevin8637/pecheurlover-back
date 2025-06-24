package com.pecheur_lover.pecheurlover.daos;

import com.pecheur_lover.pecheurlover.entities.Invoice;
import com.pecheur_lover.pecheurlover.exceptions.ResourceNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

@Repository
public class InvoiceDao {
    private final JdbcTemplate jdbcTemplate;

    public InvoiceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Invoice> invoiceRowMapper = (rs, _) -> new Invoice(
            rs.getLong("id_invoice"),
            rs.getString("email"),
            rs.getDouble("total_price"),
            rs.getDate("invoice_date")
    );

    public Invoice findById(Long id_invoice) {
        String sql = "SELECT * FROM invoice WHERE id_invoice = ?";
        return jdbcTemplate.query(sql, invoiceRowMapper, id_invoice)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Facture non trouvée"));
    }

    public List<Invoice> findByEmail(String email) {
        String sql = "SELECT * FROM invoice WHERE LOWER(email) = LOWER(?)";
        return jdbcTemplate.query(sql, new Object[]{email}, invoiceRowMapper);
    }

    public List<Invoice> findAll() {
        String sql = "SELECT * FROM invoice";
        return jdbcTemplate.query(sql, invoiceRowMapper);
    }

    public int save(String email, Date invoice_date, double total_price) {
        String sql = "INSERT INTO invoice (email, invoice_date, total_price) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.setDate(2, new java.sql.Date(invoice_date.getTime()));
            ps.setDouble(3, total_price);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public Invoice update(Long id_invoice, Invoice invoice) {
        if (!invoiceExists(id_invoice)) {
            throw new ResourceNotFoundException("Facture avec l'ID : " + id_invoice + " n'existe pas");
        }

        String sql = "UPDATE invoice SET email = ?, total_price = ?, invoice_date = ? WHERE id_invoice = ?";
        int rowsAffected = jdbcTemplate.update(sql, invoice.getEmail(), invoice.getTotal_price(), invoice.getInvoice_date(), id_invoice);

        if (rowsAffected <= 0) {
            throw new ResourceNotFoundException("Échec de la mise à jour de la facture avec l'ID : " + id_invoice);
        }
        return invoice;
    }

    public boolean delete(Long id_invoice) {
        String sql = "DELETE FROM invoice WHERE id_invoice = ?";
        return jdbcTemplate.update(sql, id_invoice) > 0;
    }

    public boolean invoiceExists(Long id_invoice) {
        String sql = "SELECT COUNT(*) FROM invoice WHERE id_invoice = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id_invoice) > 0;
    }
}
