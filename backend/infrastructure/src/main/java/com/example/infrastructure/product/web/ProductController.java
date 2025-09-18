package com.example.infrastructure.product.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.product.service.ProductService;
import com.example.infrastructure.product.dto.ProductDto;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<com.example.application.product.dto.ProductDTO> products = productService.getAll();
        List<ProductDto> productDtos = products.stream()
                .map(ProductDto::fromApplicationDto)
                .toList();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id) {
        Optional<com.example.application.product.dto.ProductDTO> product = productService.getById(id);
        return product
                .map(ProductDto::fromApplicationDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        try {
            com.example.application.product.dto.ProductDTO applicationDto = productDto.toApplicationDto();
            com.example.application.product.dto.ProductDTO createdDto = productService.create(applicationDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ProductDto.fromApplicationDto(createdDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id, @RequestBody ProductDto productDto) {
        try {
            com.example.application.product.dto.ProductDTO applicationDto = productDto.toApplicationDto();
            if (applicationDto.getId() == null) {
                applicationDto.setId(id);
            }
            
            com.example.application.product.dto.ProductDTO updatedDto = productService.update(applicationDto);
            return ResponseEntity.ok(ProductDto.fromApplicationDto(updatedDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}