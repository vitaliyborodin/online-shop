package com.vborodin.onlineshop.productservice.product.history;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductHistoryService {
    private final ProductHistoryRepository productHistoryRepository;

    Iterable<ProductHistory> findAll(Pageable pageable) {
        return productHistoryRepository.findAll(pageable);
    }

    Iterable<ProductHistory> findByProductId(Long id) {
        return productHistoryRepository.findByProductId(id);
    }
}
