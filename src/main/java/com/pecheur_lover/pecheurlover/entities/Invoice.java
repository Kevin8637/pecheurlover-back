package com.pecheur_lover.pecheurlover.entities;

import java.util.Date;

public class Invoice {
    private Long id_invoice;
    private Long id_user;
    private Long id_product;
    private Long quantity;
    private Long total_price;
    private Date invoice_date;

    public Invoice() {
    }

    public Invoice(Long id_invoice, Long id_user, Long id_product, Long quantity, Long total_price, Date invoice_date){
        this.id_invoice = id_invoice;
        this.id_user = id_user;
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

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
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
