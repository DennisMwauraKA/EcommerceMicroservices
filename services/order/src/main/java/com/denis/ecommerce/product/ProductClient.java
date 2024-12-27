package com.denis.ecommerce.product;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class ProductClient {
    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final RestTemplate restTemplate;
}
