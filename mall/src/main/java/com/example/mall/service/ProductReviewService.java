package com.example.mall.service;

import com.example.mall.dto.ProductReviewDTO;

import java.util.List;

public interface ProductReviewService {

    void addReview(ProductReviewDTO reviewDTO);

    void deleteReview(Long reviewId, Long userId);

    List<ProductReviewDTO> getReviewsByProductId(Long productId);

    List<ProductReviewDTO> getReviewsByUserId(Long userId);
}
