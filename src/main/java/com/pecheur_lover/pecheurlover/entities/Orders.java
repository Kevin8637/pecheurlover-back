package com.pecheur_lover.pecheurlover.entities;

public class Orders {
    private Long id_order;
    private Long id_invoice;
    private String email;
    private Long total_price;

    public Orders() {
    }

    public Orders(Long id_order, Long id_invoice, String email, Long total_price){
        this.id_order = id_order;
        this.id_invoice = id_invoice;
        this.email = email;
        this.total_price = total_price;
    }

    public Long getId_order() {
        return id_order;
    }

    public void setId_order(Long id_order) {
        this.id_order = id_order;
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

    public Long getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Long total_price) {
        this.total_price = total_price;
    }
}
