package com.chathuralakshan.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer's First Name is required")
        String firstName,
        @NotNull(message = "Customer's Last Name is required")
        String lastName,
        @NotNull(message = "Customer's Email is required")
        @Email(message = "Customer's Email is not a valid email address")
        String email,
        Address address
) {
}
