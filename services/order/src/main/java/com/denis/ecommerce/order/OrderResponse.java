package com.denis.ecommerce.order;

import java.math.BigDecimal;

public record OrderResponse(
      Integer id,
      BigDecimal amount,
      String reference,
      PaymentMethod paymentMethod,
      String customerId

)
 {
}
