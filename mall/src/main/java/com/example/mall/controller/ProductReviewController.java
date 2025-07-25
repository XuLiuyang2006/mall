package com.example.mall.controller;

import com.example.mall.dto.ProductReviewDTO;
import com.example.mall.service.ProductReviewService;
import com.example.mall.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    @PostMapping
    public Result<Void> addReview(@RequestBody ProductReviewDTO reviewDTO) {
        productReviewService.addReview(reviewDTO);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteReview(@PathVariable Long id, @RequestParam Long userId) {
        productReviewService.deleteReview(id, userId);
        return Result.success();
    }

    @GetMapping("/product/{productId}")
    public Result<List<ProductReviewDTO>> getReviewsByProduct(@PathVariable Long productId) {
        return Result.success(productReviewService.getReviewsByProductId(productId));
    }

    @GetMapping("/user/{userId}")
    public Result<List<ProductReviewDTO>> getReviewsByUser(@PathVariable Long userId) {
        return Result.success(productReviewService.getReviewsByUserId(userId));
    }
}
