package com.chathuralakshan.order.payment;

import com.chathuralakshan.order.customer.CustomerResponse;
import com.chathuralakshan.order.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
