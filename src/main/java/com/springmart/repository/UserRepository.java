package com.springmart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springmart.entity.Role;
import com.springmart.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	List<User> findByRole(Role role);

    List<User> findByRoleAndApproved(Role role, boolean approved);
}
