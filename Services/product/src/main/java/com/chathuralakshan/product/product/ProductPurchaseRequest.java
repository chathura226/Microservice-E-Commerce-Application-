package com.chathuralakshan.product.product;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductPurchaseRequest(
        @NotNull(message = "Product ID is required")
        Integer productId,
        @NotNull(message = "Quantity ID is required")
        double quantity
) {
}
