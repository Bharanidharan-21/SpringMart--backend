package com.springmart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springmart.entity.Product;
import com.springmart.entity.User;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByMerchant(User merchant);
    List<Product> findByCategory(String category);
    List<Product> findByIsActiveTrue();
    List<Product> findByCategoryAndIsActiveTrue(String category);
    List<Product> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);
    List<Product> findByCategoryIgnoreCaseAndIsActiveTrue(String category);
}

