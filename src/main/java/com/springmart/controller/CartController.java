package com.springmart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springmart.dto.CartResponse;
import com.springmart.service.CartService;

@RestController
@RequestMapping("/api/customer/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping("/{productId}")
    public String addToCart(
            @PathVariable Long productId,
            @RequestParam int qty,
            Principal principal) {

        return service.addToCart(productId, qty, principal.getName());
    }

    @GetMapping
    public List<CartResponse> viewCart(Principal principal) {
        return service.viewCart(principal.getName());
    }
}

