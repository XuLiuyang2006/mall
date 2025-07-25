package com.example.mall.controller;

import com.example.mall.dto.CartItemDTO;
import com.example.mall.service.CartService;
import com.example.mall.service.UserService;
import com.example.mall.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    private Long getCurrentUserId() {
        return userService.getCurrentUserId();
    }

    @PostMapping("/add")
    public Result<?> addToCart(@RequestParam Long productId,
                               @RequestParam(defaultValue = "1") Integer quantity) {
        cartService.addToCart(getCurrentUserId(), productId, quantity);
        return Result.success("添加成功");
    }

    @PutMapping("/update")
    public Result<?> updateCartItem(@RequestParam Long productId,
                                    @RequestParam Integer quantity) {
        cartService.updateQuantity(getCurrentUserId(), productId, quantity);
        return Result.success("更新成功");
    }

    @DeleteMapping("/remove")
    public Result<?> removeItem(@RequestParam Long productId) {
        cartService.removeFromCart(getCurrentUserId(), productId);
        return Result.success("删除成功");
    }

    @GetMapping
    public Result<List<CartItemDTO>> getCartItems() {
        return Result.success(cartService.getCartItems(getCurrentUserId()));
    }
}
