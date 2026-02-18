package com.springmart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springmart.entity.Review;
import com.springmart.service.ReviewService;

@RestController
@RequestMapping("/api/customer/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // ⭐ Add review
    @PostMapping("/{productId}")
    public String addReview(
            @PathVariable Long productId,
            @RequestParam int rating,
            @RequestParam String comment,
            Principal principal) {

        return reviewService.addReview(
                productId,
                rating,
                comment,
                principal.getName()
        );
    }

    // 👀 View reviews for product (public)
    @GetMapping("/{productId}")
    public List<Review> productReviews(@PathVariable Long productId) {
        return reviewService.getProductReviews(productId);
    }
}

