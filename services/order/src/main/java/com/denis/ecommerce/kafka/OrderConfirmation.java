package com.denis.ecommerce.kafka;

import com.denis.ecommerce.customer.CustomerResponse;
import com.denis.ecommerce.order.PaymentMethod;
import com.denis.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products

) {
}
