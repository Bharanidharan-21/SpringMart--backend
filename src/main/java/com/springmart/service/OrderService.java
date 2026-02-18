package com.springmart.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springmart.dto.OrderItemResponse;
import com.springmart.dto.OrderResponse;
import com.springmart.entity.CartItem;
import com.springmart.entity.Order;
import com.springmart.entity.OrderItem;
import com.springmart.entity.Product;
import com.springmart.entity.User;
import com.springmart.repository.CartRepository;
import com.springmart.repository.OrderRepository;
import com.springmart.repository.ProductRepository;
import com.springmart.repository.UserRepository;


@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartRepository cartRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;


    public OrderService(
            OrderRepository orderRepo,
            CartRepository cartRepo,
            UserRepository userRepo,
            ProductRepository productRepo) {

        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }


    // 🛒 Place order (COD)
    @Transactional
    public String placeOrder(String email) {

        User customer = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<CartItem> cartItems = cartRepo.findByCustomer(customer);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem ci : cartItems) {

            Product product = ci.getProduct();

            // 🔒 Validate stock
            if (product.getStock() < ci.getQuantity()) {
                throw new RuntimeException(
                    "Insufficient stock for product: " + product.getName()
                );
            }

            // 🔻 Reduce stock
            product.setStock(product.getStock() - ci.getQuantity());

            // 🔕 Auto deactivate if stock becomes 0
            if (product.getStock() == 0) {
                product.setActive(false);
            }

            productRepo.save(product);

            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(product);
            oi.setQuantity(ci.getQuantity());
            orderItems.add(oi);
        }

        order.setOrderItems(orderItems);
        orderRepo.save(order);

        // 🧹 Clear cart
        cartRepo.deleteByCustomer(customer);

        return "Order placed successfully (Cash on Delivery)";
    }


    // 📜 Order history
    public List<Order> orderHistory(String email) {

        User customer = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return orderRepo.findByCustomer(customer);
    }
    public List<OrderResponse> getOrders(String email) {

        User customer = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return orderRepo.findByCustomer(customer)
                .stream()
                .map(order -> {

                    OrderResponse res = new OrderResponse();
                    res.setOrderId(order.getId());
                    res.setOrderDate(order.getOrderDate());
                    res.setStatus(order.getStatus());

                    List<OrderItemResponse> items =
                            order.getOrderItems().stream().map(oi -> {
                                OrderItemResponse ir = new OrderItemResponse();
                                ir.setProductId(oi.getProduct().getId());
                                ir.setProductName(oi.getProduct().getName());
                                ir.setPrice(oi.getProduct().getPrice());
                                ir.setQuantity(oi.getQuantity());
                                return ir;
                            }).toList();

                    res.setItems(items);
                    return res;
                }).toList();
    }
}


