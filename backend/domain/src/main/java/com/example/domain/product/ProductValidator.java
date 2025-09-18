package com.example.domain.product;

public final class ProductValidator {
    private ProductValidator() {}
    
    public static ProductValidation validate(ProductId id, String code, String name, String description, 
                              byte[] image, String category, double price, int quantity, 
                              String internalReference, int shellId, 
                              Product.InventoryStatus inventoryStatus, int rating) {
        return new ProductValidation(id, code, name, description, image, category, 
                                   price, quantity, internalReference, shellId, 
                                   inventoryStatus, rating);
    }
}