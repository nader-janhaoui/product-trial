package com.example.application.product.port.in;

import com.example.domain.product.Product;

/**
 * Input port for creating a new Product aggregate.
 * Implemented by the application layer; stays framework-agnostic.
 */
public interface CreateProductUseCase {
    /**
     * Creates and persists a new product aggregate.
     * The product must already satisfy domain invariants.
     */
    Product create(Product product);
}
