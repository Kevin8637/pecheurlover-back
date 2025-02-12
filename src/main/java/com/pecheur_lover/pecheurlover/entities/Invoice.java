package com.pecheur_lover.pecheurlover.entities;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class Invoice {
    private Long id_invoice;
    private String email;
    private Long id_product;
    private Long quantity;
    @NotBlank(message = "Le prix total ne peut pas être nul")
    private Long total_price;
    @NotBlank(message = "La date de la facture ne peut pas être nulle")
    private Date invoice_date;

    public Invoice() {
    }

    public Invoice(Long id_invoice, String email, Long id_product, Long quantity, Long total_price, Date invoice_date){
        this.id_invoice = id_invoice;
        this.email = email;
        this.id_product = id_product;
        this.quantity = quantity;
        this.total_price = total_price;
        this.invoice_date = invoice_date;
    }

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

    public Long getId_product() {
        return id_product;
    }

    public void setId_product(Long id_product) {
        this.id_product = id_product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Long total_price) {
        this.total_price = total_price;
    }

    public Date getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(Date invoice_date) {
        this.invoice_date = invoice_date;
    }
}
