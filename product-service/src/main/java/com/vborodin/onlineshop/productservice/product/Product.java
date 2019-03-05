package com.vborodin.onlineshop.productservice.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vborodin.onlineshop.productservice.catalog.Catalog;
import com.vborodin.onlineshop.productservice.product.history.ProductHistory;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "PRODUCTS")
@EntityListeners(ProductListener.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String description;
    BigDecimal price;
    int quantity;
    String image;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATALOG_ID")
    Catalog catalog;
    @JsonBackReference("productHistory")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    List<ProductHistory> history;

    public Product(String name, String description, BigDecimal price, int quantity, String image, Catalog catalog) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.catalog = catalog;
    }
}
