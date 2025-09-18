package com.example.domain.product.port.in;

import com.example.domain.product.Product;

/**
 * Input port for updating an existing Product aggregate.
 */
public interface UpdateProductUseCase {
    /**
     * Updates an existing product and returns the updated aggregate.
     */
    Product update(Product product);
}
