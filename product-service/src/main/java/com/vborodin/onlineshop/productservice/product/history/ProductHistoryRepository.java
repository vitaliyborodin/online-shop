package com.vborodin.onlineshop.productservice.product.history;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductHistoryRepository extends PagingAndSortingRepository<ProductHistory, Long> {
    Iterable<ProductHistory> findByProductId(Long productId);
}
