package com.example.mall.controller;

import com.example.mall.dto.OrderDTO;
import com.example.mall.dto.UserDTO;
import com.example.mall.entity.Order;
import com.example.mall.enums.ResultCode;
import com.example.mall.exception.BizException;
import com.example.mall.service.OrderService;
import com.example.mall.service.UserService;
import com.example.mall.utils.Result;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    //GET操作
    @GetMapping("/user/{userId}")//这个查询的范围大，权限得给管理人员，用户查询自己的订单列表需要单独操作
    public Result<List<Order>> listOrderByUserId(@PathVariable Long userId){
        //先验证身份是否为管理人员
        UserDTO userDTO = userService.getUserById(userService.getCurrentUserId());
        if(!userDTO.isAdmin()){
            throw new BizException(ResultCode.USER_UNAUTHORIZED);
        }
        return Result.success(orderService.listOrdersByUser(userId));
    }

    @GetMapping("/me")
    public Result<Page<Order>> listOrderByMe(){
        Long userId = userService.getCurrentUserId();
        return Result.success(orderService.listOrdersByMe(userId));
    }

    @GetMapping("/list")
    public Result<List<Order>> listAllOrders() {
        return Result.success(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public Result<Order> getOrderById(@PathVariable Long id) {
        return Result.success(orderService.getOrderById(id));
    }

    //Post操作
    @PostMapping("/create")
    public Result<Order> create(@Valid @RequestBody OrderDTO orderDTO) {
        return Result.success(orderService.createOrder(orderDTO));
    }

    @PostMapping("/cancel/{id}")
    public Result<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return Result.success();
    }


    @PostMapping("pay/{orderId}")
    public Result<String> payOrder(@PathVariable Long orderId , HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new BizException(ResultCode.USER_UNAUTHORIZED);//检查用户是否登录
        }

        orderService.payOrder(orderId, userId);//调用支付订单方法
        return Result.success("支付成功");
    }
    // Delete操作
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return Result.success();
    }

}