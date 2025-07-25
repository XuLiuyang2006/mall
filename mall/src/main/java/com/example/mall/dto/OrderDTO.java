package com.example.mall.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class OrderDTO {

    @NotNull
    private Long userId;

    @NotNull
    private List<Item> items;

    @Data
    public static class Item {
        @NotNull
        private Long productId;

        @NotNull
        private Integer quantity;
    }
}
