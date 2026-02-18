package com.springmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springmart.entity.User;
import com.springmart.service.AdminService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 1️⃣ View all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // 2️⃣ View pending merchants
    @GetMapping("/merchants/pending")
    public ResponseEntity<List<User>> getPendingMerchants() {
        return ResponseEntity.ok(adminService.getPendingMerchants());
    }

    // 3️⃣ Approve merchant
    @PutMapping("/merchants/approve/{id}")
    public ResponseEntity<String> approveMerchant(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.approveMerchant(id));
    }

    // 4️⃣ Reject merchant
    @DeleteMapping("/merchants/reject/{id}")
    public ResponseEntity<String> rejectMerchant(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.rejectMerchant(id));
    }
}
