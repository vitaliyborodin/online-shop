package com.vborodin.onlineshop.productservice.product;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vborodin.onlineshop.productservice.catalog.Catalog;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "PRODUCTS")
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String description;
    BigDecimal price;
    int quantity;
    @JsonManagedReference("product")
    @ManyToOne
    Catalog catalog;
}
