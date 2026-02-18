package com.springmart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springmart.dto.ProductRequest;
import com.springmart.entity.Product;
import com.springmart.entity.User;
import com.springmart.repository.ProductRepository;
import com.springmart.repository.UserRepository;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public ProductService(ProductRepository productRepo, UserRepository userRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    // ➕ Add product
    public String addProduct(ProductRequest req, String merchantEmail) {

        User merchant = userRepo.findByEmail(merchantEmail)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        if (!merchant.isApproved()) {
            throw new RuntimeException("Merchant not approved");
        }

        Product product = new Product();
        product.setName(req.getName().trim());
        product.setDescription(req.getDescription().trim());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());
        product.setCategory(req.getCategory().trim());
        product.setMerchant(merchant);

        // 🔑 Auto activate only if stock > 0
        product.setActive(req.getStock() > 0);

        productRepo.save(product);
        return "Product added successfully";
    }

    // 📦 View own products (merchant can see active + inactive)
    public List<Product> getMerchantProducts(String merchantEmail) {

        User merchant = userRepo.findByEmail(merchantEmail)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        return productRepo.findByMerchant(merchant);
    }

    // ✏️ Update product
    public String updateProduct(
            Long productId,
            ProductRequest req,
            String merchantEmail) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getMerchant().getEmail().equals(merchantEmail)) {
            throw new RuntimeException("Unauthorized action");
        }

        product.setName(req.getName().trim());
        product.setDescription(req.getDescription().trim());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());
        product.setCategory(req.getCategory().trim());

        // 🔑 BUSINESS RULE: stock == 0 → inactive
        if (req.getStock() == 0) {
            product.setActive(false);
        } else {
            product.setActive(true);
        }

        productRepo.save(product);
        return "Product updated successfully";
    }

    // ❌ SOFT DELETE (Merchant)
    public String deleteProduct(Long productId, String merchantEmail) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getMerchant().getEmail().equals(merchantEmail)) {
            throw new RuntimeException("Unauthorized action");
        }

        product.setActive(false);
        productRepo.save(product);

        return "Product deactivated successfully";
    }

    // 🔥 HARD DELETE (Admin only – used later)
    public String hardDeleteProduct(Long productId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepo.delete(product);
        return "Product permanently deleted";
    }

    // Admin view all products
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
}


