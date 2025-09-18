package com.example.infrastructure.product.dto;

import java.util.Base64;

import com.example.application.product.dto.ProductDTO;

/**
 * DTO for the web layer. This converts between the application layer DTO and JSON representations.
 */
public class ProductDto {
    private String id;
    private String code;
    private String name;
    private String description;
    private String image; // Base64 encoded for JSON
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private int shellId;
    private String inventoryStatus;
    private int rating;
    private long createdAt;
    private long updatedAt;
    
    public ProductDto() {
        // Default constructor for Jackson
    }
    
    public ProductDto(String id, String code, String name, String description, String image, 
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
    
    /**
     * Convert from application DTO to web DTO
     */
    public static ProductDto fromApplicationDto(ProductDTO productDTO) {
        String base64Image = productDTO.getImage() != null ? 
            Base64.getEncoder().encodeToString(productDTO.getImage()) : null;
            
        return new ProductDto(
            productDTO.getId(),
            productDTO.getCode(),
            productDTO.getName(),
            productDTO.getDescription(),
            base64Image,
            productDTO.getCategory(),
            productDTO.getPrice(),
            productDTO.getQuantity(),
            productDTO.getInternalReference(),
            productDTO.getShellId(),
            productDTO.getInventoryStatus(),
            productDTO.getRating(),
            productDTO.getCreatedAt(),
            productDTO.getUpdatedAt()
        );
    }
    
    /**
     * Convert to application DTO
     */
    public ProductDTO toApplicationDto() {
        byte[] imageBytes = image != null ? Base64.getDecoder().decode(image) : new byte[0];
        
        ProductDTO productDTO = new ProductDTO(
            id,
            code,
            name,
            description,
            imageBytes,
            category,
            price,
            quantity,
            internalReference,
            shellId,
            inventoryStatus,
            rating,
            createdAt,
            updatedAt
        );
        
        return productDTO;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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