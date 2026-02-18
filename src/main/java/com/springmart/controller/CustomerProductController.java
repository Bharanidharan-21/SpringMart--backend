package com.springmart.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springmart.dto.ProductResponse;
import com.springmart.entity.Product;
import com.springmart.service.CustomerProductService;

@RestController
@RequestMapping("/api/customer/products")
public class CustomerProductController {

    private final CustomerProductService service;

    public CustomerProductController(CustomerProductService service) {
        this.service = service;
    }

    // 📦 View all active products
    @GetMapping
    public List<ProductResponse> allProducts() {
        return service.getAllActiveProducts();
    }


    // 🔍 Search by name
    @GetMapping("/search")
    public List<Product> searchByName(@RequestParam String name) {
        return service.searchByName(name);
    }

    // 🏷️ Search by category
    @GetMapping("/category")
    public List<Product> searchByCategory(@RequestParam String category) {
        return service.searchByCategory(category);
    }
    @GetMapping("/{id}/rating")
    public Double averageRating(@PathVariable Long id) {
        return service.getAverageRating(id);
    }
}

