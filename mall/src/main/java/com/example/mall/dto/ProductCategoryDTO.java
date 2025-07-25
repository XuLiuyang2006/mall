package com.example.mall.dto;

import lombok.Data;

@Data
public class ProductCategoryDTO {
    private Long id;
    private String name;
    private String description;
    private Integer sort;
    private Boolean visible;
}
