package com.chathuralakshan.order.order;

import com.chathuralakshan.order.customer.CustomerClient;
import com.chathuralakshan.order.exception.BusinessException;
import com.chathuralakshan.order.product.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;

    private final ProductClient productClient;

    public Integer createOrder(OrderRequest request) {
        //check the customer -->OpenFeign
        var customer=this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(()->new BusinessException("Cannot create order:: No customer exists with the provided id"));


        //purchase the product --> product microservice (Using Rest templated)



        //persist order lines

        //start payment process

        //send order confrmation --> notification microservice (kafka)


        return null;
    }
}
