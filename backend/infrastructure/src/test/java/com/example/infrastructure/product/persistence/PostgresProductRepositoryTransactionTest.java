package com.example.infrastructure.product.persistence;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.domain.product.Product;
import com.example.domain.product.Product.InventoryStatus;
import com.example.domain.product.ProductId;

@SpringBootTest(classes = TestSpringBootApplication.class)
@Testcontainers
@Transactional
@ContextConfiguration(
    initializers = PostgresTestContainerInitializer.class
)
class PostgresProductRepositoryTransactionTest {

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
    void shouldRollbackTransactionOnError() {
        // Given
        String code = "TRANS-001";
        ProductId productId = ProductId.of(UUID.randomUUID());
        Product product = new Product(
            productId, 
            code, 
            "Transaction Test Product", 
            "Transaction test description", 
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
        
        // When
        repository.save(product);
        
        // Then
        assertThat(repository.existsByCode(code)).isTrue();
    }
}