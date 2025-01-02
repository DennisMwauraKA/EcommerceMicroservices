package com.denis.ecommerce.orderLine;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(Integer id,
                               @NotNull(message = "product is  mandatory") Integer orderId,
                               Integer productId,
                               @Positive(message = "quantity is mandatory")
                               double quantity) {
}
