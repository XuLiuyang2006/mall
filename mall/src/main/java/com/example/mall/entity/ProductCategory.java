package com.example.mall.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_category")// 商品分类表
@Data
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;       // 分类名称

    private String description; // 分类描述（可选）

    private Integer sort;      // 排序字段（值越小越靠前）

    private Boolean visible;   // 是否可见，用于前台是否展示
}
