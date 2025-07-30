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
import org.springframework.transaction.annotation.Transactional;

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
//        UserDTO userDTO = userToDTO(user); // 将User对象转换为UserDTO对象
        if(user.isAdmin()){
            user.setRole("admin"); // 如果是管理员，设置角色为admin
        }else {
            user.setRole("user"); // 如果是普通用户，设置角色为user
        }
        userRepository.save(user); // 保存更新后的用户信息
        UserDTO userDTO = new UserDTO(); // 使用BeanUtils将User对象转换为UserDTO对象
        BeanUtils.copyProperties(user, userDTO); // 将User对象的属性复制到UserDTO对象中
        session.setAttribute("currentUser", userDTO); // 可选：存储当前用户信息到Session
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND));
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);// 将User对象的属性复制到UserDTO对象中
        // BeanUtils.copyProperties() 方法用于将一个对象的属性值复制到另一个对象中
        // 这里的user对象是从数据库中查询到的用户信息，dto对象是要返回给前端的用户信息
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
//        if (userId == null) {
//            throw new BizException(ResultCode.UNAUTHORIZED);
//        }// 这里的登录验证可以在Controller中使用@LoginRequired注解来处理，所以这里可以省略
        return (Long) userId;
    }

    @Override
    @Transactional
    public void updateUser(User updatedUser) {

//        Object userIdObj = session.getAttribute("userId");
//        if (userIdObj == null) {
//            throw new BizException(ResultCode.UNAUTHORIZED, "请先登录");
//        }//Controller中使用@LoginRequired注解来处理登录验证了，这里的代码可以省略

        Long userId = getCurrentUserId(); // 获取当前登录用户ID
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND));

        // 只更新允许修改的字段
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setAvatarUrl(updatedUser.getAvatarUrl());

        userRepository.save(existingUser); // 保存更新后的用户信息
    }

    private UserDTO userToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setAdmin(user.isAdmin());

        // 这里可以添加更多的转换逻辑，比如处理头像URL等
        return dto;
    }

}
