package com.karan.product_service.controller;

import com.karan.product_service.dto.ProductRequest;
import com.karan.product_service.dto.ProductResponse;
import com.karan.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // Create Product (Return ProductResponse)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductRequest createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    // Get All Products (Use HttpStatus.OK instead of FOUND)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> findAll() {
        return productService.getAllProducts();
    }

    // Get Product by ID (Use HttpStatus.OK instead of FOUND)
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse productFindById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    // Update Product (Fix method name & return ProductResponse)
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProductResponse updateProduct(@PathVariable String id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    // Delete Product by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProductById(id);
    }
}
