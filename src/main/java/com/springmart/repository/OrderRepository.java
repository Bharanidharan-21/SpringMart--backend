package com.springmart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springmart.entity.Order;
import com.springmart.entity.Product;
import com.springmart.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomer(User customer);

    boolean existsByCustomerAndOrderItems_Product(User customer, Product product);
}
