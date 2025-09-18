package com.example.application.product.port.out;

import java.util.List;
import java.util.Optional;

import com.example.domain.product.Product;
import com.example.domain.product.ProductId;

/**
 * Output port for product persistence. Implementations live in infrastructure.
 */
public interface ProductRepository {
    /** Persist or update a product aggregate. */
    Product save(Product product);

    /** Load a product by id. */
    Optional<Product> findById(ProductId id);

    /** Load all products. */
    List<Product> findAll();

    /** Delete a product by id. */
    void deleteById(ProductId id);

    /** Check existence by unique business key (e.g., code). */
    boolean existsByCode(String code);
}
