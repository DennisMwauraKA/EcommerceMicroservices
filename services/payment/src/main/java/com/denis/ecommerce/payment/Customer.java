package com.denis.ecommerce.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
        String id,
        @NotNull(message = "first name is required")
        String  firstName,
        @NotNull(message = "last name is required")
        String lastName,
        @NotNull(message = "Email is required")
        @Email(message = "Should be an email")
        String email
) {
}