package com.example.mall.service.impl;

import com.example.mall.dto.LoginDTO;
import com.example.mall.dto.UserDTO;
import com.example.mall.entity.User;
import com.example.mall.enums.ResultCode;
import com.example.mall.exception.BizException;
import com.example.mall.repository.UserRepository;
import com.example.mall.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session; // 使用Session存储用户信息

    // BCrypt加密器，用于密码加密和验证
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new BizException(ResultCode.USERNAME_ALREADY_EXISTS);
        }

        user.setPassword(encoder.encode(user.getPassword()));
        //encoder.encode(user.getPassword())转换密码为BCrypt加密格式
        //user.setPassword(...) 设置加密后的密码

        userRepository.save(user);
    }

    @Override
    public void login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (user == null || !encoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BizException(ResultCode.LOGIN_ERROR);
        }
        session.setAttribute("userId", user.getId()); // 登录成功后写入 Session
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND));
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

//    @Override
//    public UserDTO getUserByname(String name) {
//        User user = userRepository.findByUsername(name);
//        if (user == null) {
//            throw new BizException(ResultCode.USER_NOT_FOUND);
//        }
//        UserDTO dto = new UserDTO();
//        BeanUtils.copyProperties(user, dto);
//        return dto;
//    }

    @Override
    public Long getCurrentUserId() {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }
        return (Long) userId;
    }

    @Override
    public void updateUser(User updatedUser) {
        Long userId = getCurrentUserId(); // 获取当前登录用户ID
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND));

        // 只更新允许修改的字段
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setAvatarUrl(updatedUser.getAvatarUrl());

        userRepository.save(existingUser); // 保存更新后的用户信息
    }

}
