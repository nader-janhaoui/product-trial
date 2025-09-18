package com.example.application.product.port.in;

import com.example.application.product.dto.ProductDTO;

/**
 * Input port for updating an existing Product aggregate.
 */
public interface UpdateProductUseCase {
    /**
     * Updates an existing product and returns the updated aggregate.
     */
    ProductDTO update(ProductDTO productDTO);
}
