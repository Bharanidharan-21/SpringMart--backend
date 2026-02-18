package com.springmart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springmart.entity.CartItem;
import com.springmart.entity.User;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCustomer(User customer);
    void deleteByCustomer(User customer);
}

