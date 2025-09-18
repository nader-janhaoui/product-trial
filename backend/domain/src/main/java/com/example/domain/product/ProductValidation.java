package com.example.domain.product;

public record ProductValidation(
    ProductId id,
    String code,
    String name,
    String description,
    byte[] image,
    String category,
    double price,
    int quantity,
    String internalReference,
    int shellId,
    Product.InventoryStatus inventoryStatus,
    int rating
) {
    public ProductValidation {
        if (quantity < 0) throw new IllegalArgumentException("Product quantity cannot be negative");
        if (price < 0) throw new IllegalArgumentException("Product price cannot be negative");
        if (code == null || code.trim().isEmpty()) throw new IllegalArgumentException("Product code cannot be empty");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Product name cannot be empty");
        if (category == null || category.trim().isEmpty()) throw new IllegalArgumentException("Product category cannot be empty");
        if (internalReference == null || internalReference.trim().isEmpty()) throw new IllegalArgumentException("Product internal reference cannot be empty");
        if (rating < 0 || rating > 5) throw new IllegalArgumentException("Product rating must be between 0 and 5");
    }
}