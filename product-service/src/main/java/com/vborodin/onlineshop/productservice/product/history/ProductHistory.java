package com.vborodin.onlineshop.productservice.product.history;

import com.vborodin.onlineshop.productservice.catalog.Catalog;
import com.vborodin.onlineshop.productservice.product.Product;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Entity
@Data
@Table(name = "PRODUCT_HISTORY")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductHistory {
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    Product product;
    @Enumerated(STRING)
    private ActionType action;
    @Column
    @CreatedDate
    private LocalDateTime createdDate;

    public ProductHistory(Product product, ActionType action) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.image = product.getImage();
        this.catalog = product.getCatalog();
        this.product = product;
        this.action = action;
    }
}
