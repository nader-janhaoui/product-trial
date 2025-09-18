package com.example.domain.product;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void product_quantity_must_throw_for_Negative(){
       
       assertThrows(IllegalArgumentException.class, () -> {
            Product product = new Product(
            ProductId.newId(),
            "code",
            "name",
            "description",
            new byte[0],
            "category",
            10.0,
            -10,
            "internalReference",
            1,
            Product.InventoryStatus.INSTOCK,
            5,
            System.currentTimeMillis(),
            System.currentTimeMillis()
        );
        });
    }

    @Test
    void product_price_must_throw_for_Negative(){
       
       assertThrows(IllegalArgumentException.class, () -> {
            Product product = new Product(
            ProductId.newId(),
            "code",
            "name",
            "description",
            new byte[0],
            "category",
            -10.0,
            10,
            "internalReference",
            1,
            Product.InventoryStatus.INSTOCK,
            5,
            System.currentTimeMillis(),
            System.currentTimeMillis()
        );
        });
    }

    @Test
    void product_internalReference_must_throw_for_Empty(){
       assertThrows(IllegalArgumentException.class, () -> {
            Product product = new Product(
            ProductId.newId(),
            "code",
            "name",
            "description",
            new byte[0],
            "category",
            10.0,
            10,
            "",
            1,
            Product.InventoryStatus.INSTOCK,
            5,
            System.currentTimeMillis(),
            System.currentTimeMillis()
        );
        });
    }

    @Test
    void product_name_must_throw_for_Empty(){
       assertThrows(IllegalArgumentException.class, () -> {
            Product product = new Product(
            ProductId.newId(),
            "code",
            "",
            "description",
            new byte[0],
            "category",
            10.0,
            10,
            "internalReference",
            1,
            Product.InventoryStatus.INSTOCK,
            5,
            System.currentTimeMillis(),
            System.currentTimeMillis()
        );
        });
    }   

    @Test
    void product_code_must_throw_for_Empty(){
       assertThrows(IllegalArgumentException.class, () -> {
            Product product = new Product(
            ProductId.newId(),
            "",
            "name",      
            "description",
            new byte[0],
            "category",
            10.0,
            10,
            "internalReference",
            1,
            Product.InventoryStatus.INSTOCK,
            5,
            System.currentTimeMillis(),
            System.currentTimeMillis()
        );
        });
    }

    @Test
    void product_category_must_throw_for_Empty(){
         assertThrows(IllegalArgumentException.class, () -> {
            Product product = new Product(
            ProductId.newId(),
            "code",
            "name",      
            "description",
            new byte[0],
            "",
            10.0,
            10,
            "internalReference",
            1,
            Product.InventoryStatus.INSTOCK,
            5,
            System.currentTimeMillis(),
            System.currentTimeMillis()
        );
        });
    }

    @Test
    void product_rating_must_throw_for_out_of_bounds(){
        // rating must be between 0 and 5

        assertThrows(IllegalArgumentException.class, () -> {
            Product product = new Product(
            ProductId.newId(),
                "code",
                "name",      
                "description",
                new byte[0],
                "category",
                10.0,
                10,
                "internalReference",
                1,
                Product.InventoryStatus.INSTOCK,
                10,
                System.currentTimeMillis(),
                System.currentTimeMillis()
            );
        });
         assertThrows(IllegalArgumentException.class, () -> {
            Product product = new Product(
                ProductId.newId(),
                "code",
                "name",      
                "description",
                new byte[0],
                "category",
                10.0,
                10,
                "internalReference",
                1,
                Product.InventoryStatus.INSTOCK,
                -1,
                System.currentTimeMillis(),
                System.currentTimeMillis()
            );
        });
    }




}