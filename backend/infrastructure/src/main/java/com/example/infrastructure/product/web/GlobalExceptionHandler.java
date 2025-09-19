package com.example.infrastructure.product.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Invalid request body format or content");
        body.put("error", "Bad Request");
        body.put("details", ex.getMessage());
        
        System.err.println("HttpMessageNotReadableException: " + ex.getMessage());
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> body = new HashMap<>();
        String message = ex.getMessage();
        
        // Specific handling for known error messages
        if (message.contains("No enum constant")) {
            body.put("message", "Invalid inventory status. Valid values are: INSTOCK, LOWSTOCK, OUTOFSTOCK");
            body.put("errorCode", "INVALID_INVENTORY_STATUS");
        } else if (message.contains("already exists")) {
            body.put("message", "A product with this code already exists");
            body.put("errorCode", "DUPLICATE_PRODUCT_CODE");
        } else if (message.contains("Invalid UUID")) {
            body.put("message", "ID must be in UUID format (e.g., 123e4567-e89b-12d3-a456-426614174000)");
            body.put("errorCode", "INVALID_UUID_FORMAT");
        } else {
            body.put("message", message);
            body.put("errorCode", "VALIDATION_ERROR");
        }
        
        body.put("error", "Bad Request");
        body.put("status", 400);
        
        System.err.println("IllegalArgumentException: " + message);
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "An unexpected error occurred");
        body.put("error", "Internal Server Error");
        body.put("details", ex.getMessage());
        body.put("exception", ex.getClass().getName());
        body.put("status", 500);
        
        System.err.println("Unexpected exception: " + ex.getMessage());
        
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}