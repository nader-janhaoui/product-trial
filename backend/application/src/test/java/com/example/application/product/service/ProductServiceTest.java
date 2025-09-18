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
    
    @Test
    void createProduct_ShouldSaveAndReturnProduct() {
        // Arrange
        Product product = createTestProduct();
        when(productRepository.existsByCode(product.getCode())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        // Act
        Product result = productService.create(product);
        
        // Assert
        assertNotNull(result);
        assertEquals(product, result);
        verify(productRepository).existsByCode(product.getCode());
        verify(productRepository).save(product);
    }
    
    @Test
    void createProduct_WithDuplicateCode_ShouldThrowException() {
        // Arrange
        Product product = createTestProduct();
        when(productRepository.existsByCode(product.getCode())).thenReturn(true);
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.create(product);
        });
        
        assertTrue(exception.getMessage().contains("already exists"));
        verify(productRepository).existsByCode(product.getCode());
        verify(productRepository, never()).save(any(Product.class));
    }
    
    @Test
    void updateProduct_ShouldUpdateAndReturnProduct() {
        // Arrange
        Product product = createTestProduct();
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        // Act
        Product result = productService.update(product);
        
        // Assert
        assertNotNull(result);
        assertEquals(product, result);
        verify(productRepository).findById(product.getId());
        verify(productRepository).save(product);
    }
    
    @Test
    void updateProduct_WithNonExistentId_ShouldThrowException() {
        // Arrange
        Product product = createTestProduct();
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.update(product);
        });
        
        assertTrue(exception.getMessage().contains("does not exist"));
        verify(productRepository).findById(product.getId());
        verify(productRepository, never()).save(any(Product.class));
    }
    
    @Test
    void updateProduct_WithDuplicateCode_ShouldThrowException() {
        // Arrange
        Product product1 = createTestProduct();
        Product product2 = createTestProduct();
        product2.setId(ProductId.newId()); // Different ID
        
        when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.update(product1);
        });
        
        assertTrue(exception.getMessage().contains("already exists"));
        verify(productRepository).findById(product1.getId());
        verify(productRepository, never()).save(any(Product.class));
    }
    
    @Test
    void deleteProduct_ShouldDeleteExistingProduct() {
        // Arrange
        ProductId id = ProductId.newId();
        when(productRepository.findById(id)).thenReturn(Optional.of(createTestProduct()));
        
        // Act
        productService.deleteById(id);
        
        // Assert
        verify(productRepository).findById(id);
        verify(productRepository).deleteById(id);
    }
    
    @Test
    void deleteProduct_WithNonExistentId_ShouldThrowException() {
        // Arrange
        ProductId id = ProductId.newId();
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.deleteById(id);
        });
        
        assertTrue(exception.getMessage().contains("does not exist"));
        verify(productRepository).findById(id);
        verify(productRepository, never()).deleteById(any(ProductId.class));
    }
    
    @Test
    void getById_ShouldReturnProduct() {
        // Arrange
        ProductId id = ProductId.newId();
        Product product = createTestProduct();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        
        // Act
        Optional<Product> result = productService.getById(id);
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository).findById(id);
    }
    
    @Test
    void getById_WithNonExistentId_ShouldReturnEmpty() {
        // Arrange
        ProductId id = ProductId.newId();
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        
        // Act
        Optional<Product> result = productService.getById(id);
        
        // Assert
        assertFalse(result.isPresent());
        verify(productRepository).findById(id);
    }
    
    @Test
    void getAll_ShouldReturnAllProducts() {
        // Arrange
        List<Product> products = List.of(createTestProduct(), createTestProduct());
        when(productRepository.findAll()).thenReturn(products);
        
        // Act
        List<Product> result = productService.getAll();
        
        // Assert
        assertEquals(2, result.size());
        assertEquals(products, result);
        verify(productRepository).findAll();
    }
    
    private Product createTestProduct() {
        return new Product(
            ProductId.newId(),
            "TEST-001",
            "Test Product",
            "Test Description",
            new byte[0],
            "Test Category",
            99.99,
            10,
            "INT-REF-001",
            1,
            Product.InventoryStatus.INSTOCK,
            4,
            System.currentTimeMillis(),
            System.currentTimeMillis()
        );
    }
}