package com.example.mall.service.impl;

import com.example.mall.dto.ProductReviewDTO;
import com.example.mall.entity.ProductReview;
import com.example.mall.entity.User;
import com.example.mall.enums.ResultCode;
import com.example.mall.exception.BizException;
import com.example.mall.repository.ProductReviewRepository;
import com.example.mall.repository.UserRepository;
import com.example.mall.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final UserRepository userRepository;

    @Override
    public void addReview(ProductReviewDTO reviewDTO) {
        ProductReview review = new ProductReview();
        BeanUtils.copyProperties(reviewDTO, review);
        review.setCreatedAt(LocalDateTime.now());
        productReviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) {
        ProductReview review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new BizException(ResultCode.REVIEW_NOT_FOUND));

        if (!review.getUserId().equals(userId)) {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new BizException(ResultCode.USER_NOT_FOUND));
            if(!user.isAdmin()){
                throw new BizException(ResultCode.USER_UNAUTHORIZED, "只能删除自己的评论");
            }

        }
        productReviewRepository.deleteById(reviewId);
    }

    @Override
    public List<ProductReviewDTO> getReviewsByProductId(Long productId) {
        return productReviewRepository.findByProductIdOrderByCreatedAtDesc(productId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductReviewDTO> getReviewsByUserId(Long userId) {
        return productReviewRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ProductReviewDTO toDTO(ProductReview review) {
        ProductReviewDTO dto = new ProductReviewDTO();
        BeanUtils.copyProperties(review, dto);
        if (review.getCreatedAt() != null) {
            dto.setCreatedAt(review.getCreatedAt());
        }
        return dto;
    }
}
