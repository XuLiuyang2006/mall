package com.example.mall.controller;

import com.example.mall.annotation.AdminOnly;
import com.example.mall.dto.ProductCategoryDTO;
import com.example.mall.service.ProductCategoryService;
import com.example.mall.utils.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService categoryService;

    @GetMapping
    public Result<List<ProductCategoryDTO>> listCategories() {
        return Result.success(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public Result<ProductCategoryDTO> getCategory(@PathVariable Long id) {
        return Result.success(categoryService.getCategoryById(id));
    }

    @AdminOnly// 假设有一个注解用于限制管理员权限
    @PostMapping
    public Result<ProductCategoryDTO> create(@RequestBody ProductCategoryDTO dto) {
        return Result.success(categoryService.createCategory(dto));
    }

    @AdminOnly// 假设有一个注解用于限制管理员权限
    @PutMapping("/{id}")
    public Result<ProductCategoryDTO> update(@PathVariable Long id, @RequestBody ProductCategoryDTO dto) {
        return Result.success(categoryService.updateCategory(id, dto));
    }

    @AdminOnly// 假设有一个注解用于限制管理员权限
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }
}
