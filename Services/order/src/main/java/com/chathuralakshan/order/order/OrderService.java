package com.chathuralakshan.order.order;

import com.chathuralakshan.order.customer.CustomerClient;
import com.chathuralakshan.order.exception.BusinessException;
import com.chathuralakshan.order.kafka.OrderConfirmation;
import com.chathuralakshan.order.kafka.OrderProducer;
import com.chathuralakshan.order.orderline.OrderLineRequest;
import com.chathuralakshan.order.orderline.OrderLineService;
import com.chathuralakshan.order.payment.PaymentClient;
import com.chathuralakshan.order.payment.PaymentRequest;
import com.chathuralakshan.order.product.ProductClient;
import com.chathuralakshan.order.product.PurchaseRequest;
import com.chathuralakshan.order.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderProducer orderProducer;
    private final OrderLineService orderLineService;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest request) {
        //check the customer -->OpenFeign
        var customer=this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(()->new BusinessException("Cannot create order:: No customer exists with the provided id"));


        //purchase the product --> product microservice (Using Rest templated)
        var purchasedProducts=this.productClient.purchaseProducts(request.products());
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


        // start payment process
        var paymentRequest=new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );

        paymentClient.requestOrderPayment(paymentRequest);




        //send order confrmation --> notification microservice (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
            return repository.findAll()
                    .stream()
                    .map(mapper::fromOrder)
                    .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(()->new EntityNotFoundException("No order found with the provided Id:"+orderId));
    }
}
