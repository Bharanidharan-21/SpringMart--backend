package com.springmart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springmart.entity.Product;
import com.springmart.entity.Review;
import com.springmart.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByCustomerAndProduct(User customer, Product product);

    List<Review> findByProduct(Product product);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId")
    Double findAverageRating(@Param("productId") Long productId);

}

