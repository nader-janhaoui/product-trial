package com.example.infrastructure.product.persistence;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.lang.NonNull;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

public class PostgresTestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final PostgreSQLContainer<?> postgres;

    static {
        postgres = new PostgreSQLContainer<>(
                DockerImageName.parse("postgres:14-alpine").asCompatibleSubstituteFor("postgres"))
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
        
        Startables.deepStart(Stream.of(postgres)).join();
        
        // Register a shutdown hook to close the container on JVM shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(postgres::stop));
    }

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        
        // Create a new property source with the database connection details
        MapPropertySource testcontainers = new MapPropertySource("testcontainers", 
            Map.of(
                "spring.datasource.url", postgres.getJdbcUrl(),
                "spring.datasource.username", postgres.getUsername(),
                "spring.datasource.password", postgres.getPassword(),
                "spring.sql.init.mode", "always",
                "spring.sql.init.schema-locations", "classpath:schema.sql",
                "spring.flyway.enabled", "false"
            )
        );
        
        // Add the property source to the environment
        environment.getPropertySources().addFirst(testcontainers);
    }
}