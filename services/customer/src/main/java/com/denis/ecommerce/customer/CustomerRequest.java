package com.denis.ecommerce.customer;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.mongodb.core.index.Indexed;


public record CustomerRequest(
        String id,

        @NotEmpty(message = "first name should not be empty")
        String firstName,


        @NotEmpty(message = "last name should not be empty")
        String lastName,

        @Email(message = "email should be well formatted")
        @Indexed(unique = true)
        String email,


        @Valid
        Address address

) {
}
