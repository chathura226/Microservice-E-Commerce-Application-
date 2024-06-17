package com.chathuralakshan.order.order;

import com.chathuralakshan.order.customer.CustomerClient;
import com.chathuralakshan.order.exception.BusinessException;
import com.chathuralakshan.order.orderline.OrderLineRequest;
import com.chathuralakshan.order.orderline.OrderLineService;
import com.chathuralakshan.order.product.ProductClient;
import com.chathuralakshan.order.product.PurchaseRequest;
import com.chathuralakshan.order.product.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;

    private final OrderLineService orderLineService;

    public Integer createOrder(OrderRequest request) {
        //check the customer -->OpenFeign
        var customer=this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(()->new BusinessException("Cannot create order:: No customer exists with the provided id"));


        //purchase the product --> product microservice (Using Rest templated)
        this.productClient.purchaseProducts(request.products());
        var order=this.repository.save(mapper.toOrder(request));

        //persist order lines
        for(PurchaseRequest purchaseRequest:request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }


        //todo start payment process


        //send order confrmation --> notification microservice (kafka)


        return null;
    }
}
