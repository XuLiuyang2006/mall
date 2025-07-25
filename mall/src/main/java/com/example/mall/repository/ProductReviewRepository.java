package com.example.mall.repository;

import com.example.mall.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    // 查询某个商品的所有评论
    List<ProductReview> findByProductIdOrderByCreatedAtDesc(Long productId);

    // 查询某个用户的所有评论
    List<ProductReview> findByUserIdOrderByCreatedAtDesc(Long userId);
}
