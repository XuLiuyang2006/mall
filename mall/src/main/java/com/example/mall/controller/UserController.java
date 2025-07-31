package com.example.mall.controller;

import com.example.mall.annotation.LoginRequired;
import com.example.mall.annotation.OperationLog;
import com.example.mall.dto.LoginDTO;
import com.example.mall.dto.UserDTO;
import com.example.mall.entity.User;
import com.example.mall.service.UserService;
import com.example.mall.utils.Result;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    /**
     * 用户注册
     */
    @OperationLog("用户注册")
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        userService.register(user); // 内部已处理异常
        return Result.success();
    }

    /**
     * 用户登录
     */
    @OperationLog("用户登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginDTO loginDTO) {
        userService.login(loginDTO); // 登录成功会在 session 中写入 userId
        return Result.success("登录成功");
    }

    /**
     * 获取当前登录用户信息
     */
    @OperationLog("获取当前用户信息")
    @GetMapping("/me")
    @LoginRequired
    public Result<UserDTO> getUserInfo() {

        UserDTO userDTO = userService.getUserById(userService.getCurrentUserId());
        return Result.success(userDTO);
    }

    /**
     * 修改当前用户信息
     * //bug：如果用户把自己的admin字段改了怎么办。。。。。
     */
    @PutMapping("/me")
    @LoginRequired
    @OperationLog("更新用户信息")
    @Transactional // 确保事务一致性
    public Result<?> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success("更新成功");
    }

    /**
     * 用户退出登录
     */
    @OperationLog("用户退出登录")
    @PostMapping("/logout")
    public Result<?> logout() {
        session.invalidate();
        return Result.success("已退出登录");
    }
//    @GetMapping("/{name}")
//    public Result<UserDTO> getUserByName(@PathVariable String name) {
//        UserDTO userDTO = userService.getUserByname(name);
//        if (userDTO == null) {
//            throw new BizException(ResultCode.USER_NOT_FOUND);
//        }
//        return Result.success(userDTO);
//    }

}
