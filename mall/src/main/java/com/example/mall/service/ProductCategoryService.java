package com.example.mall.service;

import com.example.mall.dto.ProductCategoryDTO;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategoryDTO> getAllCategories();//获取所有商品分类

    ProductCategoryDTO getCategoryById(Long id);//根据ID获取商品分类

    ProductCategoryDTO createCategory(ProductCategoryDTO categoryDTO);//创建商品分类

    ProductCategoryDTO updateCategory(Long id, ProductCategoryDTO categoryDTO);//更新商品分类

    void deleteCategory(Long id);//删除商品分类
}
