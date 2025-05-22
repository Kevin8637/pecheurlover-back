package com.pecheur_lover.pecheurlover.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// Entité représentant un produit dans l'application
public class Product {
    private Long id_product; // Identifiant unique du produit

    // Nom du produit, obligatoire (ne peut pas être vide)
    @NotBlank(message = "Le nom d'un produit ne peut pas être vide")
    private String name;

    // Pays d'origine du produit, obligatoire
    @NotBlank(message = "Le pays ne peut pas être vide")
    private String country;

    // Description du produit, obligatoire
    @NotBlank(message = "La description ne peut pas être vide")
    private String description;

    // URL de l'image du produit, obligatoire
    @NotBlank(message = "L'url de l'image ne peut pas être vide")
    private String imageUrl;

    // Appât recommandé (optionnel)
    private String bait;

    // Conseils de cuisson (optionnel)
    private String cook_tips;

    // Conseils d'accompagnement (optionnel)
    private String vegetables_tips;

    // Prix du produit, obligatoire (ne peut pas être nul)
    @NotNull(message = "Le prix ne peut pas être nul")
    private Double price;

    // Stock disponible (optionnel)
    private Long stock;

    // Constructeur par défaut (requis pour la sérialisation/désérialisation)
    public Product(){}

    // Constructeur complet pour initialiser tous les champs
    public Product(Long id_product, String name, String country, String description, String imageUrl, String bait, String cook_tips, String vegetables_tips, Double price, Long stock){
        this.id_product = id_product;
        this.name = name;
        this.country = country;
        this.description = description;
        this.imageUrl = imageUrl;
        this.bait = bait;
        this.cook_tips = cook_tips;
        this.vegetables_tips = vegetables_tips;
        this.price = price;
        this.stock = stock;
    }

    // Getters et setters pour chaque propriété

    public Long getId_product() {
        return id_product;
    }

    public void setId_product(Long id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBait() {
        return bait;
    }

    public void setBait(String bait) {
        this.bait = bait;
    }

    public String getCook_tips() {
        return cook_tips;
    }

    public void setCook_tips(String cook_tips) {
        this.cook_tips = cook_tips;
    }

    public String getVegetables_tips() {
        return vegetables_tips;
    }

    public void setVegetables_tips(String vegetables_tips) {
        this.vegetables_tips = vegetables_tips;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
