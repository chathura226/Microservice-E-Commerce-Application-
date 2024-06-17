package com.chathuralakshan.order.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product id is mandatory")
        Integer productId,
        @Positive(message = "Quantity must be valid")
        double quantity
) {
}
