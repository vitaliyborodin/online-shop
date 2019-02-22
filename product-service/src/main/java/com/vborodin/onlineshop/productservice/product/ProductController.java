package com.vborodin.onlineshop.productservice.product;

import com.vborodin.onlineshop.productservice.exception.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/products",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@AllArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    @GetMapping
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @PostMapping
    public Product save(@RequestBody Product user) {
        return productRepository.save(user);
    }

    @GetMapping("{id}")
    public Product findById(@PathVariable("id") Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ApiException("Entity not found", HttpStatus.NOT_FOUND));
    }
}
