package com.example.mall.controller;

import com.example.mall.annotation.AdminOnly;
import com.example.mall.annotation.OperationLog;
import com.example.mall.dto.ProductDTO;
import com.example.mall.entity.Product;
import com.example.mall.enums.ResultCode;
import com.example.mall.repository.ProductRepository;
import com.example.mall.service.ProductService;
import com.example.mall.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@OperationLog("商品管理操作日志") // 假设有一个注解用于记录操作日志
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    //GET
    /**
     * 获取所有商品
     */
    @GetMapping
    public Result<List<ProductDTO>> getAllProducts() {
        return Optional.ofNullable(productService.getAll())
                .filter(list -> !list.isEmpty())
                .map(Result::success)
                .orElseGet(() -> Result.error(ResultCode.PRODUCT_EMPTY));
    }

    /**
     * 根据ID获取商品
     */
    @GetMapping("/{id}")
    public Result<ProductDTO> getProductById(@PathVariable Long id) {
        return Optional.ofNullable(productService.getById(id))
                .map(Result::success)
                .orElseGet(() -> Result.error(ResultCode.PRODUCT_NOT_FOUND));
    }

    @GetMapping("/search")
    public Result<Page<ProductDTO>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ProductDTO> result = productService.searchProducts(keyword, page, size);
        return Result.success(result);
    }


    @GetMapping("/page")
    public Result<Page<ProductDTO>> getProductPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ProductDTO> resultPage = productService.getProductPage(page, size);
        return Result.success(resultPage);
    }


    //POST
    /**
     * 新增商品
     */
    @AdminOnly // 假设有一个注解用于限制管理员权限
    @PostMapping("/add")
    public Result<ProductDTO> createProduct(@RequestBody Product product) {
        return Optional.ofNullable(product)
                .map(productService::save)
                .map(Result::success)
                .orElseGet(() -> Result.error(ResultCode.PRODUCT_SAVE_FAIL));
    }

    /**
     * 上传商品图片
     */
    @AdminOnly // 假设有一个注解用于限制管理员权限
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @RequestParam("productId") Long productId) {
        return Optional.ofNullable(productId)
                .flatMap(productRepository::findById)
                .map(product -> Optional.ofNullable(file)
                        .filter(f -> !f.isEmpty())
                        .map(f -> {
                            try {
                                String suffix = f.getOriginalFilename()
                                        .substring(f.getOriginalFilename().lastIndexOf('.'));
                                String fileName = UUID.randomUUID() + suffix;
                                String uploadDir = System.getProperty("user.dir") + "/uploaded/images/";
                                File dir = new File(uploadDir);
                                if (!dir.exists()) dir.mkdirs();

                                File dest = new File(uploadDir, fileName);
                                f.transferTo(dest);

                                product.setImageUrl("/images/" + fileName);
                                productRepository.save(product);

                                return ResponseEntity.ok("上传成功，路径: /images/" + fileName);
                            } catch (IOException e) {
                                return ResponseEntity.status(500).body("上传失败: " + e.getMessage());
                            }
                        }).orElseGet(() -> ResponseEntity.badRequest().body("上传失败，文件为空"))
                ).orElseGet(() -> ResponseEntity.badRequest().body("商品不存在，无法上传图片"));
    }


    //PUT
    /**
     * 修改商品
     */
    @AdminOnly// 假设有一个注解用于限制管理员权限
    @PutMapping("/{id}")
    public Result<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productRepository.findById(id)
                .map(existing -> {
                    existing.setName(product.getName());
                    existing.setDescription(product.getDescription());
                    existing.setPrice(product.getPrice());
                    existing.setStock(product.getStock());
                    // 不修改图片
                    Product updated = productRepository.save(existing);
                    return Result.success(productService.getById(updated.getId()));
                    //updated.getId() 调用的是Product对象，返回的是更新后的商品ID
                    //productService.getById(updated.getId()) 调用的是ProductService接口，返回的是更新后的商品DTO
                })
                .orElseGet(() -> Result.error(ResultCode.PRODUCT_NOT_FOUND));
    }


    //DELETE
    /**
     * 删除商品
     */
    @AdminOnly // 假设有一个注解用于限制管理员权限
    @DeleteMapping("/{id}")
    public Result<Void> deleteProduct(@PathVariable Long id) {
        return Optional.ofNullable(productService.getById(id))
                .map(p -> {
                    productService.delete(id);
                    return Result.<Void>success();
                })
                .orElseGet(() -> Result.error(ResultCode.PRODUCT_DELETE_FAIL));
    }


}
