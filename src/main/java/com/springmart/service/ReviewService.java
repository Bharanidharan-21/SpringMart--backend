package com.springmart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springmart.entity.Product;
import com.springmart.entity.Review;
import com.springmart.entity.User;
import com.springmart.repository.OrderRepository;
import com.springmart.repository.ProductRepository;
import com.springmart.repository.ReviewRepository;
import com.springmart.repository.UserRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    public ReviewService(
            ReviewRepository reviewRepo,
            UserRepository userRepo,
            ProductRepository productRepo,
            OrderRepository orderRepo) {

        this.reviewRepo = reviewRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    // ⭐ Add review (post-purchase only)
    public String addReview(
            Long productId,
            int rating,
            String comment,
            String email) {

        User customer = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        boolean ordered =
                orderRepo.existsByCustomerAndOrderItems_Product(customer, product);

        if (!ordered) {
            throw new RuntimeException("Purchase required to review");
        }

        if (reviewRepo.existsByCustomerAndProduct(customer, product)) {
            throw new RuntimeException("You already reviewed this product");
        }

        Review review = new Review();
        review.setCustomer(customer);
        review.setProduct(product);
        review.setRating(rating);
        review.setComment(comment);

        reviewRepo.save(review);
        return "Review submitted successfully";
    }

    // 👀 View reviews for product
    public List<Review> getProductReviews(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow();
        return reviewRepo.findByProduct(product);
    }
}


