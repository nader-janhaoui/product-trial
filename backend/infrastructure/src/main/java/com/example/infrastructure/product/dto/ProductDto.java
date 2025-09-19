package com.example.infrastructure.product.dto;

import java.math.BigDecimal;
import java.util.Base64;

import com.example.application.product.dto.ProductDTO;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for the web layer. This converts between the application layer DTO and JSON representations.
 */
@Schema(description = "Product data transfer object")
public class ProductDto {
    @Schema(description = "Unique identifier of the product", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;
    
    @Schema(description = "Code of the product", example = "P001")
    private String code;
    
    @Schema(description = "Name of the product", example = "Smartphone", requiredMode = io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED)
    private String name;
    
    @Schema(description = "Description of the product", example = "Latest model with advanced features")
    private String description;
    
    @Schema(description = "Image of the product in Base64 encoding")
    private String image; // Base64 encoded for JSON
    
    @Schema(description = "Category of the product", example = "Electronics")
    private String category;
    
    @Schema(description = "Price of the product", example = "799.99", requiredMode = io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED)
    private BigDecimal price;
    
    @Schema(description = "Quantity of the product in stock", example = "50")
    private int quantity;
    
    @Schema(description = "Internal reference code for the product")
    private String internalReference;
    
    @Schema(description = "Shell ID for the product")
    private int shellId;
    
    @Schema(
        description = "Inventory status of the product", 
        example = "INSTOCK", 
        allowableValues = {"INSTOCK", "LOWSTOCK", "OUTOFSTOCK"}
    )
    private String inventoryStatus;
    
    @Schema(description = "Rating of the product", example = "4")
    private int rating;
    
    @Schema(description = "Timestamp when the product was created", example = "1633072800000")
    private long createdAt;
    
    @Schema(description = "Timestamp when the product was last updated", example = "1633651200000")
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
        this.price = BigDecimal.valueOf(price);
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
            price != null ? price.doubleValue() : 0.0,
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    @Override
    public String toString() {
        return "ProductDto{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + (image != null ? "[image data]" : "null") + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", internalReference='" + internalReference + '\'' +
                ", shellId=" + shellId +
                ", inventoryStatus='" + inventoryStatus + '\'' +
                ", rating=" + rating +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}