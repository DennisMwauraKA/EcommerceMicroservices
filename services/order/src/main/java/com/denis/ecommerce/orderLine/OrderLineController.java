package com.denis.ecommerce.orderLine;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ap1/v1/order-lines")
public class OrderLineController {

    private final OrderLineService orderLineService;


    public OrderLineController(OrderLineService orderLineService) {
        this.orderLineService = orderLineService;

    }

    @GetMapping("/order/{order-id}")
    public ResponseEntity<List<OrderLineResponse>>getOrderId(@PathVariable("order-id")Integer orderId){
        return ResponseEntity.ok(orderLineService.getOrderId(orderId));
    }
}
