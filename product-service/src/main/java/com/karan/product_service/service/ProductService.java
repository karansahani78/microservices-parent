package com.karan.product_service.service;

import com.karan.product_service.dto.ProductRequest;
import com.karan.product_service.dto.ProductResponse;
import com.karan.product_service.model.Product;
import com.karan.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    //  Create Product (Returns ProductResponse instead of Product)
    public ProductRequest createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        Product savedProduct = productRepository.save(product);

        // Logging after product is saved to get correct ID
        log.info("Product {} is saved", savedProduct.getId());
        return new ProductRequest(
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
    // Get All Products (Returns List<ProductResponse>)
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice()
                ))
                .collect(Collectors.toList());
    }

    // Get Product by ID (Returns ProductResponse)
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for given id: " + id));

        log.info("Product {} found for given id", product.getId());

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    // Update Product (Returns ProductResponse)
    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for given id: " + id));

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());

        Product updatedProduct = productRepository.save(product);
        log.info("Product {} updated successfully", updatedProduct.getId());

        return new ProductResponse(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice()
        );
    }

    // Delete Product by ID (Fixed Exception Message)
    public void deleteProductById(String id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found for given id: " + id);
        }
        productRepository.deleteById(id);
        log.info("Product with id {} deleted successfully", id);
    }
}
