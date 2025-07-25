package com.example.mall.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {

    //Order是一个订单实体类，包含订单的基本信息
    //而OrderItem是订单中的商品项，包含每个商品的详细信息
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 订单ID

    private Long userId;

    private Double totalPrice;

    private LocalDateTime createdTime;

    private String status; // 示例：PENDING、PAID、CANCELLED 等

    private LocalDateTime payTime;//时区
}
