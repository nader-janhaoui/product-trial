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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "Product management API")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves a list of all available products")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<com.example.application.product.dto.ProductDTO> products = productService.getAll();
        List<ProductDto> productDtos = products.stream()
                .map(ProductDto::fromApplicationDto)
                .toList();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found"),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public ResponseEntity<ProductDto> getProductById(@PathVariable @Parameter(description = "Product ID") String id) {
        Optional<com.example.application.product.dto.ProductDTO> product = productService.getById(id);
        return product
                .map(ProductDto::fromApplicationDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Create product", 
        description = "Creates a new product. Note that inventoryStatus must be one of: INSTOCK, LOWSTOCK, OUTOFSTOCK"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<ProductDto> createProduct(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product information") ProductDto productDto) {
        try {
            System.out.println("Received product: " + productDto);
            System.out.println("Product name: " + productDto.getName());
            System.out.println("Product price: " + productDto.getPrice());
            
            com.example.application.product.dto.ProductDTO applicationDto = productDto.toApplicationDto();
            com.example.application.product.dto.ProductDTO createdDto = productService.create(applicationDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ProductDto.fromApplicationDto(createdDto));
        } catch (IllegalArgumentException e) {
            System.err.println("IllegalArgumentException: " + e.getMessage());
            e.printStackTrace();
            // Let the GlobalExceptionHandler handle it instead of returning ResponseEntity.badRequest().build()
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e.getMessage());
            e.printStackTrace();
            throw e; // Let global exception handler catch it
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates an existing product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable @Parameter(description = "Product ID") String id, 
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated product information") ProductDto productDto) {
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
    @Operation(summary = "Delete product", description = "Deletes a product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable @Parameter(description = "Product ID to delete") String id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}