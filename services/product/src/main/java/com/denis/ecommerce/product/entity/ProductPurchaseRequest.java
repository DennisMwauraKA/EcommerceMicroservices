package com.denis.ecommerce.product.entity;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public record ProductPurchaseRequest(

        @NotNull(message = "the product is mandatory")
        Integer id,


       @NotNull(message = "Quantity is mandatory")
       double quantity

) {
}
