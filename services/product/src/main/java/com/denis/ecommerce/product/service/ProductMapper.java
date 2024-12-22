package com.denis.ecommerce.product.service;

import com.denis.ecommerce.product.entity.Product;
import com.denis.ecommerce.product.entity.ProductRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
       return Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .availableQuantity(request.availableQuantity())
                .price(request.price())
                .category(request.category())
                .build();
    }
}
