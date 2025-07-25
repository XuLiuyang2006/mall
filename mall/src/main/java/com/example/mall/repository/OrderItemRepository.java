package com.example.mall.repository;

import com.example.mall.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    //OrderItemRepository是一个JPA仓库接口，用于操作OrderItem实体类
    //它继承自JpaRepository，提供了基本的CRUD操作
    List<OrderItem> findByOrderId(Long orderId);
}
