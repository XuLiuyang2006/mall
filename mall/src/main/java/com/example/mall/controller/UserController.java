package com.example.mall.controller;

import com.example.mall.annotation.LoginRequired;
import com.example.mall.annotation.NoLog;
import com.example.mall.annotation.OperationLog;
import com.example.mall.annotation.SensitiveField;
import com.example.mall.dto.LoginDTO;
import com.example.mall.dto.UserDTO;
import com.example.mall.entity.User;
import com.example.mall.service.UserService;
import com.example.mall.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "用户模块", description = "提供用户注册、登录、信息查询等功能")
@RequestMapping("/api/users")
@NoLog // 标记该类中的方法不记录日志
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    /**
     * 用户注册
     */
    @Operation(summary = "用户注册", description = "提供用户注册功能")
    @OperationLog("用户注册")
    @PostMapping("/register")
    public Result<?> register(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "用户注册信息")
            @RequestBody User user) {
        userService.register(user); // 内部已处理异常
        return Result.success();
    }

    /**
     * 用户登录
     */
    @Operation(summary = "用户登录", description = "提供用户登录功能")
    @OperationLog("用户登录")
    @PostMapping("/login")
    public Result<?> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "登录信息")
            @SensitiveField({"password"}) @RequestBody LoginDTO loginDTO) {
        userService.login(loginDTO); // 登录成功会在 session 中写入 userId
        return Result.success("登录成功");
    }

    /**
     * 获取当前登录用户信息
     */
    @Operation(summary = "获取当前用户信息", description = "通过 Session 获取当前登录用户的详细信息")
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
     * 修复：在前端页面中隐藏admin字段，禁止用户修改或者不支持自己修改admin字段
     */
    @Operation(summary = "更新用户信息", description = "提供更新当前登录用户信息的功能")
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
    @Operation(summary = "用户退出登录", description = "提供用户退出登录的功能")
    @LoginRequired
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
