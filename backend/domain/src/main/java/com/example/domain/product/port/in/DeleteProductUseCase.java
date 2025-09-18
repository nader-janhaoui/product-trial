package com.example.domain.product.port.in;

import com.example.domain.product.ProductId;

/**
 * Input port for deleting products.
 */
public interface DeleteProductUseCase {
    /** Delete a product by its identity. */
    void deleteById(ProductId id);
}
