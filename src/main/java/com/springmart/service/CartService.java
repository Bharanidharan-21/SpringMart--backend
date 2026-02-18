package com.springmart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springmart.dto.CartResponse;
import com.springmart.entity.CartItem;
import com.springmart.entity.Product;
import com.springmart.entity.User;
import com.springmart.repository.CartRepository;
import com.springmart.repository.ProductRepository;
import com.springmart.repository.UserRepository;

@Service
public class CartService {

    private final CartRepository cartRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;

    public CartService(CartRepository cartRepo,
                       UserRepository userRepo,
                       ProductRepository productRepo) {
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    public String addToCart(Long productId, int qty, String email) {

        User customer = userRepo.findByEmail(email).orElseThrow();
        Product product = productRepo.findById(productId).orElseThrow();

        if (!product.isActive()) {
            throw new RuntimeException("Product not available");
        }

        CartItem item = new CartItem();
        item.setCustomer(customer);
        item.setProduct(product);
        item.setQuantity(qty);

        cartRepo.save(item);
        return "Added to cart";
    }

    public List<CartResponse> viewCart(String email) {

        User customer = userRepo.findByEmail(email).orElseThrow();

        return cartRepo.findByCustomer(customer)
                .stream()
                .map(ci -> {
                    CartResponse dto = new CartResponse();
                    dto.setCartItemId(ci.getId());
                    dto.setProductId(ci.getProduct().getId());
                    dto.setProductName(ci.getProduct().getName());
                    dto.setPrice(ci.getProduct().getPrice());
                    dto.setQuantity(ci.getQuantity());
                    return dto;
                })
                .toList();
    }

}
