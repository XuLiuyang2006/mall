package com.example.mall.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class ProductDTO {
    private Long id;

    @NotBlank(message = "商品名称不能为空")
    private String name;

    private String description;
    @Min(value = 0, message = "价格不能小于0")
    private Double price;

    @Min(value = 0, message = "库存不能小于0")
    private Integer stock;

    private String imageUrl;      // 数据库存的相对路径，比如 /images/xxx.png

    private String fullImageUrl;  // 新增字段，完整图片访问地址，比如 http://localhost:8081/images/xxx.png
}
