package com.vborodin.onlineshop.productservice.catalog;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CatalogService {
    private final CatalogRepository catalogRepository;

    Iterable<Catalog> findAll() {
        return catalogRepository.findAll();
    }

    Catalog save(Catalog user) {
        return catalogRepository.save(user);
    }

    Optional<Catalog> findById(Long id) {
        return catalogRepository.findById(id);
    }
}
