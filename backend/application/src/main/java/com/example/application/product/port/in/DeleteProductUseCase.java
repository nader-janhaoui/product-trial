package com.example.application.product.port.in;

/**
 * Input port for deleting products.
 */
public interface DeleteProductUseCase {
    /** Delete a product by its identity. */
    void deleteById(String id);
}
