package com.pecheur_lover.pecheurlover.controllers;

import com.pecheur_lover.pecheurlover.daos.InvoiceDao;
import com.pecheur_lover.pecheurlover.entities.Invoice;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceDao invoiceDao;

    public InvoiceController(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Invoice>> getAllInvoices(){
        return ResponseEntity.ok(invoiceDao.findAll());
    }

    @GetMapping("/{id_invoice}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id_invoice){
        return ResponseEntity.ok(invoiceDao.findById(id_invoice));
    }

    @PostMapping("/create")
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice){
        Invoice createdInvoice = invoiceDao.save(invoice);
        return ResponseEntity.ok(createdInvoice);
    }

    @PutMapping("/{id_invoice}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id_invoice, @RequestBody Invoice invoice) {
        Invoice updatedInvoice = invoiceDao.update(id_invoice, invoice);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id_invoice}")
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable Long id_invoice) {
        if(invoiceDao.delete(id_invoice)){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
