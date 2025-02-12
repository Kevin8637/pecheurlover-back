package com.pecheur_lover.pecheurlover.daos;

import com.pecheur_lover.pecheurlover.entities.Invoice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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

    public Invoice save(Invoice invoice) {
        String sql = "INSERT INTO invoice (email, id_product, quantity, total_price, invoice_date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, invoice.getEmail(), invoice.getId_product(), invoice.getQuantity(), invoice.getTotal_price(), invoice.getInvoice_date());

        String sqlGetId = "SELECT LAST_INSERT_ID()";
        Long id_invoice = jdbcTemplate.queryForObject(sqlGetId, Long.class);

        invoice.setId_invoice(id_invoice);
        return invoice;
    }

    public Invoice update(Long id_invoice, Invoice invoice) {
        if (!invoiceExists(id_invoice)) {
            throw new RuntimeException("Facture avec l'ID : " + id_invoice + " n'existe pas");
        }

        String sql = "UPDATE invoice SET email = ?, id_product = ?, quantity = ?, total_price = ?, invoice_date = ? WHERE id_invoice = ?";
        int rowsAffected = jdbcTemplate.update(sql, invoice.getEmail(), invoice.getId_product(), invoice.getQuantity(), invoice.getTotal_price(), invoice.getInvoice_date(), id_invoice);

        if (rowsAffected <= 0) {
            throw new RuntimeException("Échec de la mise à jour de la facture avec l'ID : " + id_invoice);
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
