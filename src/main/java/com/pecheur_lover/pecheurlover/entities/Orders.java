package com.pecheur_lover.pecheurlover.entities;

import jakarta.validation.constraints.NotNull;
import java.util.Date;

// Entité représentant une commande (Order) dans l'application
public class Orders {
    // Identifiant du produit commandé (obligatoire)
    @NotNull(message = "L'id du produit ne peut pas être nul")
    private Long id_product;

    // Identifiant de la facture associée à la commande (obligatoire)
    @NotNull(message = "L'id de la facture ne peut pas être nul")
    private Long id_invoice;

    // Quantité commandée (obligatoire)
    @NotNull(message = "La quantité ne peut pas être nulle")
    private Long quantity;

    // Prix unitaire du produit (obligatoire)
    @NotNull(message = "Le prix ne peut pas être nul")
    private Double price;

    // Nom du produit (optionnel, utile pour l'affichage)
    private String productName;

    // Image du produit (optionnel, utile pour l'affichage)
    private String productImage;

    // Date de la facture associée (optionnel, pour l'affichage ou l'historique)
    private Date invoiceDate;

    // Prix total de la commande (optionnel, calculé ou récupéré)
    private Double totalPrice;

    // Constructeur par défaut (requis pour la sérialisation/désérialisation)
    public Orders() {
    }

    // Constructeur complet pour initialiser tous les champs
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

    // Getters et setters pour chaque propriété

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
