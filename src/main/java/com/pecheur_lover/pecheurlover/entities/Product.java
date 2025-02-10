package com.pecheur_lover.pecheurlover.entities;

import org.w3c.dom.Text;

public class Product {
    private Long id_product;
    private String name;
    private String type;
    private String description;
    private String imageUrl;
    private String cook_tips;
    private String vegetables_tips;
    private Long price;
    private Long stock;

    public Product(){}

    public Product(Long id_product, String name, String type, String description, String imageUrl, String cook_tips, String vegetables_tips, Long price, Long stock){
        this.id_product = id_product;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
