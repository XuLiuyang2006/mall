package com.example.mall.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id; // 用户ID

    @Column (nullable = false, unique = true)
    private String username; // 用户名，唯一

    @Column(nullable = false)
    private String password; // 密码，存储加密后的值

    private String email;

    private String phone;

    private String avatarUrl;

    private String role = "user";

    private Integer status = 0;

    private boolean admin = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


};