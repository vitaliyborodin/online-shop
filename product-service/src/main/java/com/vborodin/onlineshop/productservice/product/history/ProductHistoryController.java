package com.vborodin.onlineshop.productservice.product.history;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/history",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@AllArgsConstructor
public class ProductHistoryController {
    private final ProductHistoryService productHistoryService;

    @GetMapping
    public Iterable<ProductHistory> findAll(Pageable pageable) {
        return productHistoryService.findAll(pageable);
    }

    @GetMapping("/product/{id}")
    public Iterable<ProductHistory> findByProductId(@PathVariable("id") Long id) {
        return productHistoryService.findByProductId(id);
    }
}
