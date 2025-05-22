package com.pecheur_lover.pecheurlover.entities;

import jakarta.validation.constraints.Email;
import java.util.Date;

// Entité représentant une facture (Invoice) dans l'application
public class Invoice {
    private Long id_invoice; // Identifiant unique de la facture

    @Email
    private String email; // Email du client associé à la facture

    private Double total_price; // Montant total de la facture

    private Date invoice_date; // Date de création de la facture

    // Constructeur sans argument (requis par certains frameworks)
    public Invoice() {
    }

    // Constructeur complet pour initialiser tous les champs
    public Invoice(Long id_invoice, String email, Double total_price, Date invoice_date){
        this.id_invoice = id_invoice;
        this.email = email;
        this.total_price = total_price;
        this.invoice_date = invoice_date;
    }

    // Getters et setters pour chaque propriété

    public Long getId_invoice() {
        return id_invoice;
    }

    public void setId_invoice(Long id_invoice) {
        this.id_invoice = id_invoice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public Date getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(Date invoice_date) {
        this.invoice_date = invoice_date;
    }
}
