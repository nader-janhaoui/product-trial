package com.example.infrastructure.product.persistence;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.application.product.port.out.ProductRepository;
import com.example.domain.product.Product;
import com.example.domain.product.ProductId;

/**
 * Contract test that defines the expected behavior of any ProductRepository implementation.
 * Framework-agnostic test to verify the repository contract is fulfilled.
 */
public abstract class ProductRepositoryContractTest {

    protected abstract ProductRepository getRepository();

    protected abstract void clearRepository();

    private Product testProduct;

    @BeforeEach
    void setUp() {
        clearRepository();
        testProduct = createTestProduct("TEST-001", "Test Product 1");
    }

    @Test
    void saveProduct_ShouldPersistProduct() {
        // When
        Product savedProduct = getRepository().save(testProduct);

        // Then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isEqualTo(testProduct.getId());
        assertThat(savedProduct.getCode()).isEqualTo(testProduct.getCode());
        assertThat(savedProduct.getName()).isEqualTo(testProduct.getName());
    }

    @Test
    void findById_WithExistingProduct_ShouldReturnProduct() {
        // Given
        Product savedProduct = getRepository().save(testProduct);

        // When
        Optional<Product> result = getRepository().findById(savedProduct.getId());

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(savedProduct.getId());
        assertThat(result.get().getCode()).isEqualTo(savedProduct.getCode());
        assertThat(result.get().getName()).isEqualTo(savedProduct.getName());
        assertThat(result.get().getDescription()).isEqualTo(savedProduct.getDescription());
        assertThat(result.get().getPrice()).isEqualTo(savedProduct.getPrice());
        assertThat(result.get().getQuantity()).isEqualTo(savedProduct.getQuantity());
        assertThat(result.get().getInventoryStatus()).isEqualTo(savedProduct.getInventoryStatus());
    }

    @Test
    void findById_WithNonExistingProduct_ShouldReturnEmpty() {
        // When
        Optional<Product> result = getRepository().findById(ProductId.newId());

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void findAll_WithMultipleProducts_ShouldReturnAllProducts() {
        // Given
        getRepository().save(testProduct);
        getRepository().save(createTestProduct("TEST-002", "Test Product 2"));
        getRepository().save(createTestProduct("TEST-003", "Test Product 3"));

        // When
        List<Product> products = getRepository().findAll();

        // Then
        assertThat(products).hasSize(3);
        assertThat(products).extracting(Product::getCode)
                .containsExactlyInAnyOrder("TEST-001", "TEST-002", "TEST-003");
    }

    @Test
    void deleteById_ShouldRemoveProduct() {
        // Given
        Product savedProduct = getRepository().save(testProduct);
        assertThat(getRepository().findById(savedProduct.getId())).isPresent();

        // When
        getRepository().deleteById(savedProduct.getId());

        // Then
        assertThat(getRepository().findById(savedProduct.getId())).isEmpty();
    }

    @Test
    void existsByCode_WithExistingCode_ShouldReturnTrue() {
        // Given
        getRepository().save(testProduct);

        // When
        boolean exists = getRepository().existsByCode(testProduct.getCode());

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void existsByCode_WithNonExistingCode_ShouldReturnFalse() {
        // When
        boolean exists = getRepository().existsByCode("NON-EXISTENT");

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    void update_ShouldModifyExistingProduct() {
        // Given
        Product savedProduct = getRepository().save(testProduct);
        savedProduct.setName("Updated Name");
        savedProduct.setPrice(199.99);
        savedProduct.setQuantity(50);

        // When
        Product updatedProduct = getRepository().save(savedProduct);

        // Then
        assertThat(updatedProduct.getName()).isEqualTo("Updated Name");
        assertThat(updatedProduct.getPrice()).isEqualTo(199.99);
        assertThat(updatedProduct.getQuantity()).isEqualTo(50);

        // Verify the changes persisted
        Optional<Product> retrievedProduct = getRepository().findById(savedProduct.getId());
        assertThat(retrievedProduct).isPresent();
        assertThat(retrievedProduct.get().getName()).isEqualTo("Updated Name");
        assertThat(retrievedProduct.get().getPrice()).isEqualTo(199.99);
        assertThat(retrievedProduct.get().getQuantity()).isEqualTo(50);
    }

    protected Product createTestProduct(String code, String name) {
        return new Product(
                ProductId.newId(),
                code,
                name,
                "Test Description",
                new byte[0],
                "Test Category",
                99.99,
                10,
                "INT-REF-" + code,
                1,
                Product.InventoryStatus.INSTOCK,
                4,
                System.currentTimeMillis(),
                System.currentTimeMillis()
        );
    }
}