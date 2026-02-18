package com.springmart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmart.entity.Role;
import com.springmart.entity.User;
import com.springmart.repository.UserRepository;


@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    // View all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // View pending merchants
    public List<User> getPendingMerchants() {
        return userRepository.findByRoleAndApproved(Role.MERCHANT, false);
    }

    // Approve merchant
    public String approveMerchant(Long merchantId) {

        User merchant = userRepository.findById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        if (merchant.getRole() != Role.MERCHANT) {
            throw new RuntimeException("User is not a merchant");
        }

        merchant.setApproved(true);
        userRepository.save(merchant);

        return "Merchant approved successfully";
    }

    // Reject merchant (optional – delete)
    public String rejectMerchant(Long merchantId) {

        User merchant = userRepository.findById(merchantId)
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        if (merchant.getRole() != Role.MERCHANT) {
            throw new RuntimeException("User is not a merchant");
        }

        userRepository.delete(merchant);
        return "Merchant rejected and removed";
    }
}

