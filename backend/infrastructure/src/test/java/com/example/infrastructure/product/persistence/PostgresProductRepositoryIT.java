package com.example.infrastructure.product.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = TestSpringBootApplication.class)
@ActiveProfiles("test")
@Testcontainers
@ContextConfiguration(
    initializers = PostgresTestContainerInitializer.class
)
class PostgresProductRepositoryIT {
    
    @Autowired
    private PostgresProductRepository postgresProductRepository;
    
    @Test
    void contextLoads() {
        // This test verifies that the Spring context can start up correctly
        assert(postgresProductRepository != null);
    }
}