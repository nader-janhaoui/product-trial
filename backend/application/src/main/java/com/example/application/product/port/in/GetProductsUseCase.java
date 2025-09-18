package com.example.application.product.port.in;

import java.util.List;
import java.util.Optional;

import com.example.application.product.dto.ProductDTO;

/**
 * Input port for querying products.
 */
public interface GetProductsUseCase {
    /** Find a product by its identity. */
    Optional<ProductDTO> getById(String id);

    /** Retrieve all products. */
    List<ProductDTO> getAll();
}
