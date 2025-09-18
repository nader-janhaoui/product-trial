package com.example.domain.product.port.in;

import java.util.List;
import java.util.Optional;

import com.example.domain.product.Product;
import com.example.domain.product.ProductId;

/**
 * Input port for querying products.
 */
public interface GetProductsUseCase {
    /** Find a product by its identity. */
    Optional<Product> getById(ProductId id);

    /** Retrieve all products. */
    List<Product> getAll();
}
