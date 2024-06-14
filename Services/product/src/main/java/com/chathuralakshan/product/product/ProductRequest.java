package com.chathuralakshan.product.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        Integer id,
        @NotNull(message = "Product Name is required")
        String name,
        @NotNull(message = "Product Description is required")
        String description,
        @Positive(message = "Product Available Quantity must be positive")
        double availableQuantity,
        @Positive(message = "Price must be positive")
        BigDecimal price,
        @NotNull(message = "Product Category is required")
        Integer categoryId
) {
}
