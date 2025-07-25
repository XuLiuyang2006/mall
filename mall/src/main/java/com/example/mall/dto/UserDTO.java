package com.example.mall.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String avatarUrl;
    private String role;
    private Integer status;
    private boolean admin;
}

