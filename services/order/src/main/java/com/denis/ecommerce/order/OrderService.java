package com.denis.ecommerce.order;

import com.denis.ecommerce.customer.CustomerClient;
import com.denis.ecommerce.product.ProductClient;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final CustomerClient customerClient;
private final ProductClient productClient;
    public OrderService(CustomerClient customerClient, ProductClient productClient) {
        this.customerClient = customerClient;
        this.productClient = productClient;
    }

    public Integer createOrder(OrderRequest orderRequest) {
        // check the customer using id
var customer = this.customerClient.findCustomerById(orderRequest.customerId()).orElseThrow(()->new RuntimeException("Customer not found"));
//purchase the product(using rest template)
        return null;
    }
}
