package com.vborodin.onlineshop.productservice.product;

import com.vborodin.onlineshop.productservice.exception.ApiException;
import com.vborodin.onlineshop.productservice.util.FileStorageService;
import com.vborodin.onlineshop.productservice.util.UploadFileResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/products",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final FileStorageService fileStorageService;

    @GetMapping
    public Iterable<Product> findAll(Pageable pageable) {
        return productService.findAll(pageable);
    }

    @PostMapping
    public Product save(@RequestBody Product user) {
        return productService.save(user);
    }

    @GetMapping("{id}")
    public Product findById(@PathVariable("id") Long id) {
        return productService.findById(id)
                .orElseThrow(() -> new ApiException("Entity not found", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public Page<Product> findByCatalogId(Pageable pageable, @RequestParam(value = "catalogId", required = false) Long catalogId) {
        return productService.findByCatalogId(pageable, catalogId);
    }

    @PostMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,
                                         @PathVariable("id") Long id) {

        Product product = productService.findById(id)
                .orElseThrow(() -> new ApiException("Entity not found", HttpStatus.NOT_FOUND));

        String fileName = fileStorageService.storeFile(file);
        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(fileStorageService.getFileUploadDir())
                .path("/")
                .path(fileName)
                .toUriString();

        product.setImage(fileName);
        productService.save(product);

        return new UploadFileResponse(fileName, fileUri,
                file.getContentType(), file.getSize());
    }
}
