package com.example.application.product.dto;

import com.example.domain.product.Product;
import com.example.domain.product.ProductId;

/**
 * DTO for the application layer. This isolates the domain model from external layers.
 */
public class ProductDTO {
    private String id;
    private String code;
    private String name;
    private String description;
    private byte[] image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private int shellId;
    private String inventoryStatus;
    private int rating;
    private long createdAt;
    private long updatedAt;
    
    public ProductDTO() {
        // Default constructor
    }
    
    public ProductDTO(String id, String code, String name, String description, byte[] image, 
                    String category, double price, int quantity, String internalReference, 
                    int shellId, String inventoryStatus, int rating, long createdAt, long updatedAt) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.image = image;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.internalReference = internalReference;
        this.shellId = shellId;
        this.inventoryStatus = inventoryStatus;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public static ProductDTO fromDomain(Product product) {
        return new ProductDTO(
            product.getId().toString(),
            product.getCode(),
            product.getName(),
            product.getDescription(),
            product.getImage(),
            product.getCategory(),
            product.getPrice(),
            product.getQuantity(),
            product.getInternalReference(),
            product.getShellId(),
            product.getInventoryStatus().name(),
            product.getRating(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }
    
    public Product toDomain() {
        ProductId productId = id != null ? ProductId.of(id) : ProductId.newId();
        
        Product.InventoryStatus inventoryStatusEnum = 
            inventoryStatus != null ? Product.InventoryStatus.valueOf(inventoryStatus) : Product.InventoryStatus.INSTOCK;
        
        return new Product(
            productId,
            code,
            name,
            description,
            image,
            category,
            price,
            quantity,
            internalReference,
            shellId,
            inventoryStatusEnum,
            rating,
            createdAt,
            updatedAt
        );
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getInternalReference() {
        return internalReference;
    }

    public void setInternalReference(String internalReference) {
        this.internalReference = internalReference;
    }

    public int getShellId() {
        return shellId;
    }

    public void setShellId(int shellId) {
        this.shellId = shellId;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }
}