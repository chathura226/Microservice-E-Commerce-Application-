package com.chathuralakshan.order.exception;

import com.chathuralakshan.order.order.OrderRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
   private final String msg;

}
