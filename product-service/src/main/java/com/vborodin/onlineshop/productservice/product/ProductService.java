package com.vborodin.onlineshop.productservice.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    Product save(Product user) {
        return productRepository.save(user);
    }

    Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
}
