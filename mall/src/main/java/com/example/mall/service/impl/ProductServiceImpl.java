package com.example.mall.service.impl;

import com.example.mall.dto.ProductDTO;
import com.example.mall.entity.Product;
import com.example.mall.enums.ResultCode;
import com.example.mall.exception.BizException;
import com.example.mall.repository.ProductRepository;
import com.example.mall.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j // ✅ 开启日志功能
@Service
public class ProductServiceImpl implements ProductService {

    @Value("${app.image-base-url}")
    private String imageBaseUrl;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDTO getById(Long id) {
        log.info("开始查询商品，id={}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("未找到商品，id={}", id);
                    return new BizException(ResultCode.PRODUCT_NOT_FOUND);
                });
        log.info("查询成功，商品名={}", product.getName());
        return convertToDTO(product);
    }

    @Override
    public List<ProductDTO> getAll() {
        log.info("获取所有商品信息");
        List<Product> list = productRepository.findAll();
        if (list == null || list.isEmpty()) {
            log.warn("商品列表为空");
            throw new BizException(ResultCode.PRODUCT_EMPTY);
        }
        log.info("查询到 {} 个商品", list.size());
        return list.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO save(Product product) {
        if (product == null) {
            log.error("保存失败：商品为 null");
            throw new BizException(ResultCode.PRODUCT_SAVE_FAIL);
        }
        Product saved = productRepository.save(product);
        log.info("商品保存成功，id={}, name={}", saved.getId(), saved.getName());
        return convertToDTO(saved);
    }

    @Override
    public void delete(Long id) {
        log.info("准备删除商品，id={}", id);
        if (!productRepository.existsById(id)) {
            log.warn("删除失败：商品不存在，id={}", id);
            throw new BizException(ResultCode.PRODUCT_DELETE_FAIL);
        }
        productRepository.deleteById(id);
        log.info("商品删除成功，id={}", id);
    }

    @Override
    public Page<ProductDTO> searchProducts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findByNameContaining(keyword, pageable);
        List<ProductDTO> dtoList = productPage.getContent()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, productPage.getTotalElements());
    }


    // DTO 转换，拼接完整图片 URL
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());

        String imagePath = product.getImageUrl();
        if (imagePath != null && !imagePath.isEmpty()) {
            dto.setImageUrl(imageBaseUrl + imagePath);
        } else {
            dto.setImageUrl(null);
        }

        return dto;
    }

    @Override
    public Page<ProductDTO> getProductPage(int page, int size) {
        //Pageable对象，指定页码和每页大小
        //PageRequest.of(page, size) 创建一个Pageable对象，创建生成相应的SQL语句查询
        Pageable pageable = PageRequest.of(page, size);
        //把Pageable对象传入到查询方法中
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductDTO> dtoList = productPage.getContent()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, productPage.getTotalElements());
    }
}
