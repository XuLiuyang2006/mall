package com.example.mall.repository;

import com.example.mall.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;


public interface ProductRepository extends JpaRepository<Product, Long> {
    // 模糊搜索：商品名称中包含关键词
    Page<Product> findByNameContaining(String keyword, Pageable pageable);
}
