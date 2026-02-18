package com.springmart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springmart.dto.OrderResponse;
import com.springmart.service.OrderService;

@RestController
@RequestMapping("/api/customer/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 🛒 Place order
    @PostMapping
    public String placeOrder(Principal principal) {
        return orderService.placeOrder(principal.getName());
    }

    // 📜 View order history
    @GetMapping
    public List<OrderResponse> myOrders(Principal principal) {
        return orderService.getOrders(principal.getName());
    }
}

