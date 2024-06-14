package com.chathuralakshan.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true) //callsuper class
@Data
public class CustomerNotFoundException extends RuntimeException {
    private final String msg;
}
