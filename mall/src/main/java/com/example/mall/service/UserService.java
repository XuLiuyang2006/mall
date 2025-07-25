package com.example.mall.service;

import com.example.mall.dto.LoginDTO;
import com.example.mall.dto.UserDTO;
import com.example.mall.entity.User;

public interface UserService {
    UserDTO getUserById(Long id);
    void login(LoginDTO loginDTO);
    void register(User user);
//    UserDTO getUserByname(String name);
    Long getCurrentUserId(); // 新增：获取当前登录用户ID
    void updateUser(User user); // 新增：更新用户信息
}
