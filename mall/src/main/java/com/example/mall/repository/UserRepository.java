package com.example.mall.repository;

import com.example.mall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    // Additional query methods can be defined here if needed
}
