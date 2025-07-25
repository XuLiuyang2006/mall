package com.example.mall.controller;

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

    @PostMapping
    public Result<ProductCategoryDTO> create(@RequestBody ProductCategoryDTO dto) {
        return Result.success(categoryService.createCategory(dto));
    }

    @PutMapping("/{id}")
    public Result<ProductCategoryDTO> update(@PathVariable Long id, @RequestBody ProductCategoryDTO dto) {
        return Result.success(categoryService.updateCategory(id, dto));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }
}
