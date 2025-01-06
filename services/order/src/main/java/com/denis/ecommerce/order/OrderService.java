package com.denis.ecommerce.order;

import com.denis.ecommerce.customer.CustomerClient;
import com.denis.ecommerce.kafka.OrderConfirmation;
import com.denis.ecommerce.kafka.OrderProducer;
import com.denis.ecommerce.orderLine.OrderLineRequest;
import com.denis.ecommerce.orderLine.OrderLineService;
import com.denis.ecommerce.payment.PaymentClient;
import com.denis.ecommerce.payment.PaymentRequest;
import com.denis.ecommerce.product.ProductClient;
import com.denis.ecommerce.product.PurchaseRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public OrderService(CustomerClient customerClient, ProductClient productClient, OrderRepository orderRepository, OrderMapper orderMapper, OrderLineService orderLineService, OrderProducer orderProducer, PaymentClient paymentClient) {
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderLineService = orderLineService;
        this.orderProducer = orderProducer;
        this.paymentClient = paymentClient;
    }

    //create an order
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
// initiate payment
        var paymentRequest = new PaymentRequest(
                orderRequest.amount(),
                orderRequest.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestPayment(
                paymentRequest
        );
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

    // get all orders
    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toFindAllOrders).collect(Collectors.toList());
    }


    // get order by id
    public OrderResponse findOrderById(Integer id) {
        return orderRepository.findById(id).map(orderMapper::toFindAllOrders).orElseThrow(() -> new RuntimeException("Order not found"));

    }


}
