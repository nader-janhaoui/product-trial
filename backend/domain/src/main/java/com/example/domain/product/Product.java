package com.example.domain.product;


public class Product {

    ProductId id;
    String code;
    String name;
    String description;
    byte[] image;
    String category;
    double price;
    int quantity;
    String internalReference;
    int shellId;
    InventoryStatus inventoryStatus;
    int rating;
    long createdAt;
    long updatedAt;

    public enum InventoryStatus {
        INSTOCK, LOWSTOCK, OUTOFSTOCK
    }
    public Product(ProductId id, String code, String name, String description, byte[] image, String category, double price, int quantity, String internalReference, int shellId, InventoryStatus inventoryStatus, int rating, long createdAt, long updatedAt) {
        var validation = ProductValidator.validate(id, code, name, description, image, category, price, quantity, internalReference, shellId, inventoryStatus, rating);
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
    // getters and setters
    public ProductId getId() { return id; }
    public void setId(ProductId id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getInternalReference() { return internalReference; }
    public void setInternalReference(String internalReference) { this.internalReference = internalReference; }
    public int getShellId() { return shellId; }
    public void setShellId(int shellId) { this.shellId = shellId; }
    public InventoryStatus getInventoryStatus() { return inventoryStatus; }
    public void setInventoryStatus(InventoryStatus inventoryStatus) { this.inventoryStatus = inventoryStatus; }


}




