package com.pecheur_lover.pecheurlover.entities;

import jakarta.validation.constraints.NotNull;
import java.util.Date;

public class Orders {
    @NotNull(message = "L'id du produit ne peut pas être nul")
    private Long id_product;

    @NotNull(message = "L'id de la facture ne peut pas être nul")
    private Long id_invoice;

    @NotNull(message = "La quantité ne peut pas être nulle")
    private Long quantity;

    @NotNull(message = "Le prix ne peut pas être nul")
    private Double price;

    private String productName;

    private String productImage;

    private Date invoiceDate;

    private Double totalPrice;

    public Orders() {
    }

    public Orders(Long id_product, Long id_invoice, Long quantity, Double price, String productName, String productImage, Date invoiceDate, Double totalPrice) {
        this.id_product = id_product;
        this.id_invoice = id_invoice;
        this.quantity = quantity;
        this.price = price;
        this.productName = productName;
        this.productImage = productImage;
        this.invoiceDate = invoiceDate;
        this.totalPrice = totalPrice;
    }

    public Long getId_product() {
        return id_product;
    }

    public void setId_product(Long id_product) {
        this.id_product = id_product;
    }

    public Long getId_invoice() {
        return id_invoice;
    }

    public void setId_invoice(Long id_invoice) {
        this.id_invoice = id_invoice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
