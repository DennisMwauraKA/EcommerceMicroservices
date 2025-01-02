package com.denis.ecommerce.order;

import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest orderRequest) {
        return Order.builder()
                .id(orderRequest.id())
                .reference(orderRequest.reference())
                .customerId(orderRequest.customerId())
                .paymentMethod(orderRequest.paymentMethod())
                .total(orderRequest.amount())


                .build();
    }
}
