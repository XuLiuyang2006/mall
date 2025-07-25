package com.example.mall.service;

import com.example.mall.dto.CartItemDTO;

import java.util.List;

public interface CartService {
    void addToCart(Long userId, Long productId, int quantity);
    void updateQuantity(Long userId, Long productId, int quantity);
    void removeFromCart(Long userId, Long productId);
    List<CartItemDTO> getCartItems(Long userId);
}
