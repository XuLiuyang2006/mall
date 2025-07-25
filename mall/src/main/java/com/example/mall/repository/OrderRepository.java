package com.example.mall.repository;

import com.example.mall.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // OrderRepository是一个JPA仓库接口，用于操作Order实体类
    // 它继承自JpaRepository，提供了基本的CRUD操作
    // 可以在这里添加自定义查询方法

    // 不分页（管理员查询）
    List<Order> findByUserId(Long userId);
    // 分页查询（用户查询自己的订单）
    Page<Order> findByUserId(Long userId, Pageable pageable);
}
