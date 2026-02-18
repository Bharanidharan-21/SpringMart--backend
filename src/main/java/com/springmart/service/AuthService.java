package com.springmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springmart.dto.LoginRequest;
import com.springmart.dto.LoginResponse;
import com.springmart.dto.RegisterRequest;
import com.springmart.entity.Role;
import com.springmart.entity.User;
import com.springmart.repository.UserRepository;
import com.springmart.security.JwtUtil;

@Service
public class AuthService {
	@Autowired
	private UserRepository repo;
	
	@Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;
    
    public String register(RegisterRequest req) {

        // 🔒 Trim everything
        String name = req.getName().trim();
        String email = req.getEmail().trim();
        String password = req.getPassword().trim();
        String confirmPassword = req.getConfirmPassword().trim();
        String phone = req.getPhoneNumber().trim();
        String address = req.getAddress().trim();

        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match");
        }

        if (req.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin registration not allowed");
        }

        if (repo.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setPhoneNumber(phone);
        user.setAddress(address);
        user.setRole(req.getRole());

        user.setApproved(req.getRole() == Role.CUSTOMER);

        repo.save(user);
        return "Registration successful";
    }

        
    public LoginResponse login(LoginRequest req) {

        // Admin login
        if (req.getEmail().equals("admin@springmart.com")
                && req.getPassword().equals("admin")) {

            String token = jwtUtil.generateToken("admin@springmart.com");
            return new LoginResponse(token, "ADMIN");
        }

        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                req.getEmail(),
                req.getPassword()
            )
        );

        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(token, user.getRole().name());
    }

}
