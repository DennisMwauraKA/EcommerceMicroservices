package com.denis.ecommerce.customer;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.validation.annotation.Validated;


@Getter
@Setter

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @NotEmpty(message = "Street should not be empty")
    private String street;
    @NotEmpty(message = "HouseNumber should not be empty")
    private String houseNumber;
    @NotEmpty(message = "Zipcode should not be empty")
    private String zipCode;
}
