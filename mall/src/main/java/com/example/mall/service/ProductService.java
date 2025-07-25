package com.example.mall.service;

import com.example.mall.dto.ProductDTO;
import com.example.mall.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    // 修改后的接口定义
    List<ProductDTO> getAll();
    ProductDTO getById(Long id);
    ProductDTO save(Product product);
    void delete(Long id);
    // 新增分页查询方法
    Page<ProductDTO> getProductPage(int page, int size);
    Page<ProductDTO> searchProducts(String keyword, int page, int size);

}
