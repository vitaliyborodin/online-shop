package com.vborodin.onlineshop.productservice;

import com.vborodin.onlineshop.productservice.model.Category;
import com.vborodin.onlineshop.productservice.model.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RequestMapping("/products")
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	private List<Product> productList = Arrays.asList(
			new Product(1L, "Nokia 3310", "", new BigDecimal(100),  new Category(2L, "Nokia", null)),
			new Product(2L, "iPhone X", "", new BigDecimal(1000), new Category(2L, "Apple", null))
	);

	@GetMapping("")
	public List<Product> findAllProdcuts() {
		return productList;
	}

	@GetMapping("/{productId}")
	public Product findProduct(@PathVariable Long productId) {
		return productList.stream().filter(b -> b.getId().equals(productId)).findFirst().orElse(null);
	}
}

