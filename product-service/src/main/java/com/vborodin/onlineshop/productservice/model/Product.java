package com.vborodin.onlineshop.productservice.model;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String name;
    private String decription;
    private BigDecimal price;
    private Category category;

    public Product(Long id, String name, String decription, BigDecimal price, Category category) {
        this.id = id;
        this.name = name;
        this.decription = decription;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
