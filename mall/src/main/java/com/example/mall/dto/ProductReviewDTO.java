package com.example.mall.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductReviewDTO {

    private Long id;

    private Long productId;// 商品ID

    private Long userId;

    private Integer rating;// 1 到 5 星评分

    private String content;// 评论内容

    private LocalDateTime createdAt;// 创建时间
}
