package com.example.mall.service.impl;

import com.example.mall.dto.ProductCategoryDTO;
import com.example.mall.entity.ProductCategory;
import com.example.mall.enums.ResultCode;
import com.example.mall.exception.BizException;
import com.example.mall.repository.ProductCategoryRepository;
import com.example.mall.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository categoryRepository;

    @Override
    public List<ProductCategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(category -> {
            ProductCategoryDTO dto = new ProductCategoryDTO();
            BeanUtils.copyProperties(category, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public ProductCategoryDTO getCategoryById(Long id) {
        ProductCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new BizException(ResultCode.CATEGORY_NOT_FOUND));
        ProductCategoryDTO dto = new ProductCategoryDTO();
        BeanUtils.copyProperties(category, dto);
        return dto;
    }

    @Override
    public ProductCategoryDTO createCategory(ProductCategoryDTO dto) {
        ProductCategory category = new ProductCategory();
        BeanUtils.copyProperties(dto, category);
        category = categoryRepository.save(category);
        BeanUtils.copyProperties(category, dto);
        return dto;
    }

    @Override
    public ProductCategoryDTO updateCategory(Long id, ProductCategoryDTO dto) {
        ProductCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new BizException(ResultCode.CATEGORY_NOT_FOUND));
        BeanUtils.copyProperties(dto, category, "id");
        category = categoryRepository.save(category);
        BeanUtils.copyProperties(category, dto);
        return dto;
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new BizException(ResultCode.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
    }
}
