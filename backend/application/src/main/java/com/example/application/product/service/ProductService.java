package com.example.application.product.service;

import java.util.List;
import java.util.Optional;

import com.example.application.product.port.in.CreateProductUseCase;
import com.example.application.product.port.in.DeleteProductUseCase;
import com.example.application.product.port.in.GetProductsUseCase;
import com.example.application.product.port.in.UpdateProductUseCase;
import com.example.application.product.port.out.ProductRepository;
import com.example.domain.product.Product;
import com.example.domain.product.ProductId;

/**
 * Application service implementing all product-related use cases.
 * Acts as the primary entry point to product management functionality.
 */
public class ProductService implements CreateProductUseCase, UpdateProductUseCase, 
                                      DeleteProductUseCase, GetProductsUseCase {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        // Check for duplicate product code
        if (productRepository.existsByCode(product.getCode())) {
            throw new IllegalArgumentException("Product with code " + product.getCode() + " already exists");
        }
        
        // Set creation/update timestamps if they're not already set
        long currentTime = System.currentTimeMillis();
        if (product.getCreatedAt() == 0) {
            product.setCreatedAt(currentTime);
        }
        if (product.getUpdatedAt() == 0) {
            product.setUpdatedAt(currentTime);
        }
        
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        // Verify product exists
        ProductId id = product.getId();
        if (!productRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        }
        
        // Check if updating to a code that already exists on another product
        Optional<Product> existingProductWithCode = productRepository.findAll().stream()
            .filter(p -> p.getCode().equals(product.getCode()) && !p.getId().equals(id))
            .findFirst();
            
        if (existingProductWithCode.isPresent()) {
            throw new IllegalArgumentException("Product with code " + product.getCode() + " already exists");
        }
        
        // Update timestamp
        product.setUpdatedAt(System.currentTimeMillis());
        
        return productRepository.save(product);
    }

    @Override
    public void deleteById(ProductId id) {
        // Verify product exists before deletion
        if (!productRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        }
        
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> getById(ProductId id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}