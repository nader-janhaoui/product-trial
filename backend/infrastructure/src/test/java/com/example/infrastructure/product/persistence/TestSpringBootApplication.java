package com.example.infrastructure.product.persistence;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * This configuration class is needed for Spring Boot tests to find a configuration
 * since there's no main Spring Boot application class in the test classpath.
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("com.example.infrastructure.product.persistence")
public class TestSpringBootApplication {
    // This empty class is needed to provide a Spring Boot configuration for tests
}