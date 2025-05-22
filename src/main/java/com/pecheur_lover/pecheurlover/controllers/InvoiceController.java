package com.pecheur_lover.pecheurlover.controllers;

import com.pecheur_lover.pecheurlover.daos.InvoiceDao;
import com.pecheur_lover.pecheurlover.entities.Invoice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

// Contrôleur REST pour la gestion des factures (Invoice)
@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    // Injection du DAO pour interagir avec la base de données des factures
    private final InvoiceDao invoiceDao;

    public InvoiceController(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    // Récupérer toutes les factures
    @GetMapping("/all")
    public ResponseEntity<List<Invoice>> getAllInvoices(){
        return ResponseEntity.ok(invoiceDao.findAll());
    }

    // Récupérer une facture par son identifiant
    @GetMapping("/{id_invoice}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id_invoice){
        return ResponseEntity.ok(invoiceDao.findById(id_invoice));
    }

    // Récupérer toutes les factures associées à un email donné
    @GetMapping("/email/{email}")
    public ResponseEntity<List<Invoice>> getInvoicesByEmail(@PathVariable String email){
        List<Invoice> invoices = invoiceDao.findByEmail(email);
        return ResponseEntity.ok(invoices);
    }

    // Créer une nouvelle facture à partir des informations reçues dans la requête
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createInvoice(@RequestBody Map<String, Object> request) {
        String email = (String) request.get("email");
        Date invoice_date = new Date(); // Date de création de la facture (maintenant)
        double total_price = ((Number) request.get("total_price")).doubleValue();

        int id_invoice = invoiceDao.save(email, invoice_date, total_price);

        // Retourne les informations de la facture nouvellement créée
        return ResponseEntity.ok(Map.of(
                "id_invoice", id_invoice,
                "email", email,
                "invoice_date", invoice_date,
                "total_price", total_price
        ));
    }

    // Mettre à jour une facture existante
    @PutMapping("/{id_invoice}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id_invoice, @RequestBody Invoice invoice) {
        Invoice updatedInvoice = invoiceDao.update(id_invoice, invoice);
        return ResponseEntity.ok(updatedInvoice);
    }

    // Supprimer une facture par son identifiant
    @DeleteMapping("/{id_invoice}")
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable Long id_invoice) {
        if(invoiceDao.delete(id_invoice)){
            return ResponseEntity.noContent().build(); // Succès : pas de contenu à retourner
        } else {
            return ResponseEntity.notFound().build(); // Erreur : facture non trouvée
        }
    }
}
