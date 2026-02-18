package com.springmart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springmart.entity.Product;
import com.springmart.service.ProductService;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> allProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> hardDeleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.hardDeleteProduct(id));
    }
}

