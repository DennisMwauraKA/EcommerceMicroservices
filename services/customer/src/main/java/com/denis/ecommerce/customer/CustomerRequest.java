package com.denis.ecommerce.customer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


public record CustomerRequest(
        String id,

        @NotEmpty(message = "first name should not be empty")
        String firstName,


        @NotEmpty(message = "last name should not be empty")
        String lastName,

        @Email(message = "email should be well formatted")
        String email,

        @NotEmpty(message = "Address should not be empty")
        Address address

) {
}
