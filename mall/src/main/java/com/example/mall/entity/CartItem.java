package com.example.mall.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// Cart Item ID

    private Long userId;

    private Long productId;

    private Integer quantity;
}
