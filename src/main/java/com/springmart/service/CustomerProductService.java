package com.springmart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springmart.dto.ProductResponse;
import com.springmart.entity.Product;
import com.springmart.repository.ProductRepository;
import com.springmart.repository.ReviewRepository;

@Service
public class CustomerProductService {

    private final ProductRepository productRepo;
    private final ReviewRepository reviewRepo;

    public CustomerProductService(ProductRepository productRepo, ReviewRepository reviewRepo) {
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
    }

    // 📦 View all active products
    public List<ProductResponse> getAllActiveProducts() {
        return productRepo.findByIsActiveTrue()
                .stream()
                .map(p -> {
                    ProductResponse dto = new ProductResponse();
                    dto.setId(p.getId());
                    dto.setName(p.getName());
                    dto.setDescription(p.getDescription());
                    dto.setPrice(p.getPrice());
                    dto.setStock(p.getStock());
                    dto.setCategory(p.getCategory());
                    dto.setActive(p.isActive());
                    dto.setMerchantName(p.getMerchant().getName());
                    return dto;
                })
                .toList();
    }


    // 🔍 Search by name
    public List<Product> searchByName(String name) {
        return productRepo
                .findByNameContainingIgnoreCaseAndIsActiveTrue(name);
    }

    // 🏷️ Search by category
    public List<Product> searchByCategory(String category) {
        return productRepo
                .findByCategoryIgnoreCaseAndIsActiveTrue(category);
    }
    public Double getAverageRating(Long productId) {
		return reviewRepo.findAverageRating(productId);
    }
}
