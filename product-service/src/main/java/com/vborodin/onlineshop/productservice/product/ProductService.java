package com.vborodin.onlineshop.productservice.product;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional()
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    Iterable<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    Product save(Product product) {
        return productRepository.save(product);
    }

    Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    Page<Product> findByCatalogId(Pageable pageable, Long id) {
        return productRepository.findByCatalogId(pageable, id);
    }
}
