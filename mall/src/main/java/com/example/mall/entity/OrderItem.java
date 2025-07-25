package com.example.mall.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem {

    //Order是一个订单实体类，包含订单的基本信息
    //而OrderItem是订单中的商品项，包含每个商品的详细信息
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;// 商品项ID

    private Long orderId;//订单ID
    private Long productId;
    private Integer quantity;
    private Double price;
}
