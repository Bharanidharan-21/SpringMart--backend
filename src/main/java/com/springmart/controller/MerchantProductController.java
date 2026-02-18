package com.springmart.controller;

import java.security.Principal;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.springmart.dto.ProductRequest;
import com.springmart.entity.Product;
import com.springmart.service.ProductService;

@RestController
@RequestMapping("/api/merchant/products")
public class MerchantProductController {

    private final ProductService productService;

    public MerchantProductController(ProductService productService) {
        this.productService = productService;
    }

    // ➕ Add product
    @PostMapping
    public String addProduct(
            @Valid @RequestBody ProductRequest request,
            Principal principal) {
        return productService.addProduct(request, principal.getName());
    }

    // 📦 View own products
    @GetMapping
    public List<Product> myProducts(Principal principal) {
        return productService.getMerchantProducts(principal.getName());
    }
    
 // ✏️ Edit own product
    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id,@Valid @RequestBody ProductRequest request,Principal principal) {

        return productService.updateProduct(id,request,principal.getName()
        );
    }
    
    // ❌ Delete own product
    @DeleteMapping("/{id}")
    public String deleteProduct(
            @PathVariable Long id,
            Principal principal) {
        return productService.deleteProduct(id, principal.getName());
    }
}

