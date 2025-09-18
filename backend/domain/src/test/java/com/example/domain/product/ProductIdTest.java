package com.example.domain.product;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ProductIdTest {

    // Add test for mangodb uuid format
    // Add test for equals and hashcode
    // add test for pstgressql if format

    @Test
    void productId_of_string_must_throw_for_invalid_uuid(){
        assertThrows(IllegalArgumentException.class, () -> {
            ProductId id = ProductId.of("invalid-uuid");
        });
    }
    @Test
    void productId_of_uuid_must_throw_for_null(){
        assertThrows(NullPointerException.class, () -> {
            ProductId id = ProductId.of((java.util.UUID) null);
        });
    }
    @Test
    void productId_of_correct_uuid_string_must_create_instance(){
        ProductId id = ProductId.of("123e4567-e89b-12d3-a456-426614174000");
        assert(id.value().toString().equals("123e4567-e89b-12d3-a456-426614174000"));
    }
    @Test
    void productId_of_correct_uuid_must_create_instance(){
        java.util.UUID uuid = java.util.UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        ProductId id = ProductId.of(uuid);
        assert(id.value().equals(uuid));    
    }

    @Test
    void productId_of_Long_must_create_instance(){
        ProductId id = ProductId.of(123456789L);
        assert(id.toLong().equals(123456789L));
    }

}
