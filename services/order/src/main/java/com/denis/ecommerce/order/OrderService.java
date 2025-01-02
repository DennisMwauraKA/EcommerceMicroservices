package com.denis.ecommerce.order;

import com.denis.ecommerce.customer.CustomerClient;
import com.denis.ecommerce.kafka.OrderConfirmation;
import com.denis.ecommerce.kafka.OrderProducer;
import com.denis.ecommerce.orderLine.OrderLineRequest;
import com.denis.ecommerce.orderLine.OrderLineService;
import com.denis.ecommerce.product.ProductClient;
import com.denis.ecommerce.product.PurchaseRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public OrderService(CustomerClient customerClient, OrderLineService orderLineService, ProductClient productClient, OrderRepository orderRepository, OrderMapper orderMapper, OrderProducer orderProducer) {
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderLineService = orderLineService;
        this.orderProducer = orderProducer;
    }

    public Integer createOrder(OrderRequest orderRequest) {
        // check the customer using id
        var customer = this.customerClient.findCustomerById(orderRequest.customerId()).orElseThrow(() -> new RuntimeException("Customer not found"));
//purchase the product(using rest template)
        var purchasedProducts = this.productClient.purchaseProducts(orderRequest.products());
        //persist order
        var order = this.orderRepository.save(orderMapper.toOrder(orderRequest));
        //persist orderLines
        for (PurchaseRequest purchaseRequest : orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    ));

        }
        orderProducer.sendOrderConfirmation(

                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        return order.getId();
    }
}
