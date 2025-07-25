package com.example.mall.service.impl;

import com.example.mall.dto.CartItemDTO;
import com.example.mall.entity.CartItem;
import com.example.mall.entity.Product;
import com.example.mall.enums.ResultCode;
import com.example.mall.exception.BizException;
import com.example.mall.repository.CartItemRepository;
import com.example.mall.repository.ProductRepository;
import com.example.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public void addToCart(Long userId, Long productId, int quantity) {
        //添加到用户购物车的时候，必须判断ta添加的商品是否存在和数量范围是否合理
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BizException(ResultCode.NOT_FOUND, "商品不存在"));

        if (quantity <= 0) {
            throw new BizException(ResultCode.PARAM_ERROR, "数量必须大于 0");
        }

        if (quantity > product.getStock()) {
            throw new BizException(ResultCode.PARAM_ERROR, "库存不足");
        }

        CartItem item = cartItemRepository.findByUserIdAndProductId(userId, productId)
                .orElse(null);

        if (item != null) {
            int newQuantity = item.getQuantity() + quantity;//新的数量是原来的数量加上新添加的数量
            if (newQuantity > product.getStock()) {
                throw new BizException(ResultCode.PARAM_ERROR, "库存不足");
            }
            item.setQuantity(newQuantity);
        } else {
            //如果购物车中没有该商品，则创建一个新的购物车项
            item = new CartItem();
            item.setUserId(userId);
            item.setProductId(productId);
            item.setQuantity(quantity);
        }

        cartItemRepository.save(item);
    }
    //这个方法用于添加商品到购物车，如果购物车中已经存在该商品，则更新数量，否则创建新的购物车项。
    //在更新数量之前，会检查商品是否存在、数量是否合理以及库存是否充足。
    //数量是否合理：新添加的数量必须大于 0，且不能超过商品的库存、添加了之后的数量不能超过商品的库存。

    @Override
    @Transactional
    public void updateQuantity(Long userId, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new BizException(ResultCode.PARAM_ERROR, "数量必须大于 0");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BizException(ResultCode.NOT_FOUND, "商品不存在"));

        if (quantity > product.getStock()) {
            throw new BizException(ResultCode.PARAM_ERROR, "库存不足");
        }

        CartItem item = cartItemRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new BizException(ResultCode.NOT_FOUND, "购物车中无此商品"));

        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    @Override
    @Transactional
    public void removeFromCart(Long userId, Long productId) {
        CartItem item = cartItemRepository.findByUserIdAndProductId(userId, productId)
                .orElse(null);
        if (item != null) {
            cartItemRepository.delete(item);
        }
    }


    @Override
    public List<CartItemDTO> getCartItems(Long userId) {
        List<CartItem> items = cartItemRepository.findByUserId(userId);

        return items.stream().map(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElse(null);

            CartItemDTO dto = new CartItemDTO();
            dto.setProductId(item.getProductId());
            dto.setQuantity(item.getQuantity());
            if (product != null) {
                dto.setProductName(product.getName());
                dto.setPrice(product.getPrice());
                dto.setImageUrl(product.getImageUrl());
            }
            return dto;
        }).collect(Collectors.toList());
    }
}
