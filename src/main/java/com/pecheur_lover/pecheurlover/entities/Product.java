package com.pecheur_lover.pecheurlover.entities;

import jakarta.validation.constraints.NotBlank;
import org.w3c.dom.Text;

public class Product {
    private Long id_product;
    @NotBlank(message = "Le nom d'un produit ne peut pas être vide")
    private String name;
    private String country;
    @NotBlank(message = "La description ne peut pas être vide")
    private String description;
    private String imageUrl;
    private String cook_tips;
    private String vegetables_tips;
    private Double price;
    private Long stock;

    public Product(){}

    public Product(Long id_product, String name, String country, String description, String imageUrl, String cook_tips, String vegetables_tips, Double price, Long stock){
        this.id_product = id_product;
        this.name = name;
        this.country = country;
        this.description = description;
        this.imageUrl = imageUrl;
        this.cook_tips = cook_tips;
        this.vegetables_tips = vegetables_tips;
        this.price = price;
        this.stock = stock;
    }

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
