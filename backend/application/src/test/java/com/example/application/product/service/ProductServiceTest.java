package com.example.application.product.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.application.product.port.out.ProductRepository;
import com.example.application.product.dto.ProductDTO;
import com.example.domain.product.Product;
import com.example.domain.product.ProductId;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    
    private ProductService productService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }
    
    // Utility method for converting from Product to ProductDTO
    private ProductDTO convertToDTO(Product product) {
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
    
    @Test
    void createProduct_ShouldSaveAndReturnProduct() {
        // Arrange
        Product product = createTestProduct();
        ProductDTO productDTO = convertToDTO(product);
        when(productRepository.existsByCode(product.getCode())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        // Act
        ProductDTO result = productService.create(productDTO);
        
        // Assert
        assertNotNull(result);
        assertEquals(productDTO.getId(), result.getId());
        assertEquals(productDTO.getCode(), result.getCode());
        verify(productRepository).existsByCode(product.getCode());
        verify(productRepository).save(any(Product.class));
    }
    
    @Test
    void createProduct_WithDuplicateCode_ShouldThrowException() {
        // Arrange
        Product product = createTestProduct();
        ProductDTO productDTO = convertToDTO(product);
        when(productRepository.existsByCode(product.getCode())).thenReturn(true);
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> productService.create(productDTO));
        
        assertTrue(exception.getMessage().contains("already exists"));
        verify(productRepository).existsByCode(product.getCode());
        verify(productRepository, never()).save(any(Product.class));
    }
    
    @Test
    void updateProduct_ShouldUpdateAndReturnProduct() {
        // Arrange
        Product product = createTestProduct();
        ProductDTO productDTO = convertToDTO(product);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        // Act
        ProductDTO result = productService.update(productDTO);
        
        // Assert
        assertNotNull(result);
        assertEquals(productDTO.getId(), result.getId());
        verify(productRepository).findById(product.getId());
        verify(productRepository).save(any(Product.class));
    }
    
    @Test
    void updateProduct_WithNonExistentId_ShouldThrowException() {
        // Arrange
        Product product = createTestProduct();
        ProductDTO productDTO = convertToDTO(product);
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> productService.update(productDTO));
        
        assertTrue(exception.getMessage().contains("does not exist"));
        verify(productRepository).findById(product.getId());
        verify(productRepository, never()).save(any(Product.class));
    }
    
    @Test
    void updateProduct_WithDuplicateCode_ShouldThrowException() {
        // Arrange
        Product product1 = createTestProduct();
        ProductDTO productDTO1 = convertToDTO(product1);
        
        Product product2 = createTestProduct();
        product2.setId(ProductId.newId()); // Different ID
        product2.setCode("TEST-001"); // Same code
        
        when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> productService.update(productDTO1));
        
        assertTrue(exception.getMessage().contains("already exists"));
        verify(productRepository).findById(product1.getId());
        verify(productRepository, never()).save(any(Product.class));
    }
    
    @Test
    void deleteProduct_ShouldDeleteExistingProduct() {
        // Arrange
        ProductId id = ProductId.newId();
        String idString = id.toString();
        when(productRepository.findById(id)).thenReturn(Optional.of(createTestProduct()));
        
        // Act
        productService.deleteById(idString);
        
        // Assert
        verify(productRepository).findById(id);
        verify(productRepository).deleteById(id);
    }
    
    @Test
    void deleteProduct_WithNonExistentId_ShouldThrowException() {
        // Arrange
        ProductId id = ProductId.newId();
        String idString = id.toString();
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> productService.deleteById(idString));
        
        assertTrue(exception.getMessage().contains("does not exist"));
        verify(productRepository).findById(id);
        verify(productRepository, never()).deleteById(any(ProductId.class));
    }
    
    @Test
    void getById_ShouldReturnProduct() {
        // Arrange
        ProductId id = ProductId.newId();
        String idString = id.toString();
        Product product = createTestProduct();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        
        // Act
        Optional<ProductDTO> result = productService.getById(idString);
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals(product.getId().toString(), result.get().getId());
        verify(productRepository).findById(id);
    }
    
    @Test
    void getById_WithNonExistentId_ShouldReturnEmpty() {
        // Arrange
        ProductId id = ProductId.newId();
        String idString = id.toString();
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act
        Optional<ProductDTO> result = productService.getById(idString);
        
        // Assert
        assertFalse(result.isPresent());
        verify(productRepository).findById(id);
    }
    
    @Test
    void getAll_ShouldReturnAllProducts() {
        // Arrange
        Product product1 = createTestProduct();
        Product product2 = createTestProduct();
        product2.setId(ProductId.newId());
        product2.setCode("TEST-002");
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        
        // Act
        List<ProductDTO> results = productService.getAll();
        
        // Assert
        assertEquals(2, results.size());
        verify(productRepository).findAll();
    }
    
    private Product createTestProduct() {
        ProductId id = ProductId.newId();
        String code = "TEST-001";
        String name = "Test Product";
        String description = "Test Description";
        byte[] image = new byte[0];
        String category = "Test Category";
        double price = 99.99;
        int quantity = 10;
        String internalReference = "INT-REF-001";
        int shellId = 1;
        Product.InventoryStatus inventoryStatus = Product.InventoryStatus.INSTOCK;
        int rating = 4;
        long timestamp = System.currentTimeMillis();
        
        return new Product(
            id, code, name, description, image, category, 
            price, quantity, internalReference, shellId, 
            inventoryStatus, rating, timestamp, timestamp
        );
    }
}