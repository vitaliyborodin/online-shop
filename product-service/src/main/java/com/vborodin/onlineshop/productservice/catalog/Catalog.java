package com.vborodin.onlineshop.productservice.catalog;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vborodin.onlineshop.productservice.product.Product;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "CATALOGS")
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATALOG_ID")
    Catalog parent;
    @JsonBackReference("catalog")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    List<Catalog> children;
    @JsonBackReference("product")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "catalog")
    List<Product> products;
}
