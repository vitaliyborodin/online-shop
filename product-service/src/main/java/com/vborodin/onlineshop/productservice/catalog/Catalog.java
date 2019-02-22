package com.vborodin.onlineshop.productservice.catalog;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference("catalog")
    @ManyToOne
    @JoinColumn(name = "CATALOG_ID")
    Catalog parent;
    @JsonBackReference("catalog")
    @OneToMany(mappedBy = "id")
    List<Catalog> children;
    @JsonBackReference("product")
    @OneToMany(mappedBy = "id")
    List<Product> products;
}
