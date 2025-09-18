package com.example.infrastructure.product.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.domain.product.Product;
import com.example.domain.product.Product.InventoryStatus;
import com.example.domain.product.ProductId;

@SpringBootTest(classes = TestSpringBootApplication.class)
@Testcontainers
@ContextConfiguration(
    initializers = PostgresTestContainerInitializer.class
)
class PostgresProductRepositoryTest {

    @Autowired
    private PostgresProductRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        // Clear the database before each test
        jdbcTemplate.update("DELETE FROM products");
    }

    @Test
    void shouldSaveAndRetrieveProduct() {
        // Given
        String code = "TEST-001";
        ProductId productId = ProductId.of(UUID.randomUUID());
        Product product = new Product(
            productId, 
            code, 
            "Test Product", 
            "Test description", 
            new byte[0], // image placeholder
            "Electronics", 
            19.99, 
            10, 
            "SKU001", 
            1, 
            InventoryStatus.INSTOCK, 
            5, 
            System.currentTimeMillis(), 
            System.currentTimeMillis()
        );

        // When
        repository.save(product);
        Optional<Product> found = repository.findById(productId);

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getCode()).isEqualTo(code);
        assertThat(found.get().getName()).isEqualTo("Test Product");
        assertThat(found.get().getPrice()).isEqualTo(19.99);
    }

    @Test
    void shouldFindAllProducts() {
        // Given
        ProductId productId1 = ProductId.of(UUID.randomUUID());
        ProductId productId2 = ProductId.of(UUID.randomUUID());
        
        Product product1 = new Product(
            productId1, 
            "TEST-001", 
            "Test Product 1", 
            "Description 1", 
            new byte[0], 
            "Electronics", 
            19.99, 
            10, 
            "SKU001", 
            1, 
            InventoryStatus.INSTOCK, 
            5, 
            System.currentTimeMillis(), 
            System.currentTimeMillis()
        );
        Product product2 = new Product(
            productId2, 
            "TEST-002", 
            "Test Product 2", 
            "Description 2", 
            new byte[0], 
            "Electronics", 
            29.99, 
            15, 
            "SKU002", 
            2, 
            InventoryStatus.INSTOCK, 
            4, 
            System.currentTimeMillis(), 
            System.currentTimeMillis()
        );
        repository.save(product1);
        repository.save(product2);

        // When
        List<Product> products = repository.findAll();

        // Then
        assertThat(products).hasSize(2);
    }

    @Test
    void shouldCheckIfProductExistsByCode() {
        // Given
        String code = "TEST-001";
        ProductId productId = ProductId.of(UUID.randomUUID());
        Product product = new Product(
            productId, 
            code, 
            "Test Product", 
            "Description", 
            new byte[0], 
            "Electronics", 
            19.99, 
            10, 
            "SKU001", 
            1, 
            InventoryStatus.INSTOCK, 
            5, 
            System.currentTimeMillis(), 
            System.currentTimeMillis()
        );
        repository.save(product);

        // When & Then
        assertThat(repository.existsByCode(code)).isTrue();
        assertThat(repository.existsByCode("NONEXISTENT")).isFalse();
    }

    @Test
    void shouldDeleteProduct() {
        // Given
        String code = "TEST-001";
        ProductId productId = ProductId.of(UUID.randomUUID());
        Product product = new Product(
            productId, 
            code, 
            "Test Product", 
            "Description", 
            new byte[0], 
            "Electronics", 
            19.99, 
            10, 
            "SKU001", 
            1, 
            InventoryStatus.INSTOCK, 
            5, 
            System.currentTimeMillis(), 
            System.currentTimeMillis()
        );
        repository.save(product);

        // When
        repository.deleteById(productId);

        // Then
        assertThat(repository.findById(productId)).isEmpty();
    }

    @Test
    void shouldUpdateExistingProduct() {
        // Given
        String code = "TEST-001";
        ProductId productId = ProductId.of(UUID.randomUUID());
        Product product = new Product(
            productId, 
            code, 
            "Test Product", 
            "Description", 
            new byte[0], 
            "Electronics", 
            19.99, 
            10, 
            "SKU001", 
            1, 
            InventoryStatus.INSTOCK, 
            5, 
            System.currentTimeMillis(), 
            System.currentTimeMillis()
        );
        repository.save(product);

        // When
        Product updatedProduct = new Product(
            productId, 
            code, 
            "Updated Product", 
            "Updated Description", 
            new byte[0], 
            "Electronics", 
            29.99, 
            15, 
            "SKU001", 
            1, 
            InventoryStatus.INSTOCK, 
            5, 
            System.currentTimeMillis(), 
            System.currentTimeMillis()
        );
        repository.save(updatedProduct);

        // Then
        Optional<Product> found = repository.findById(productId);
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Updated Product");
        assertThat(found.get().getPrice()).isEqualTo(29.99);
    }
}