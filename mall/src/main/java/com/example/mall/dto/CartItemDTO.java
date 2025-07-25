package com.example.mall.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long productId;
    private String productName;
    private String imageUrl;
    private Double price;
    private Integer quantity;
}
