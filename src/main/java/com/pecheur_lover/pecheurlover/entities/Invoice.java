package com.pecheur_lover.pecheurlover.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public class Invoice {
    private Long id_invoice;
    @Email
    private String email;
    private Double total_price;
    private Date invoice_date;


    public Invoice() {
    }

    public Invoice(Long id_invoice, String email, Double total_price, Date invoice_date){
        this.id_invoice = id_invoice;
        this.email = email;
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
