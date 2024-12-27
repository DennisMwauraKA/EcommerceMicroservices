package com.denis.ecommerce.product.service;

import com.denis.ecommerce.category.entity.Category;
import com.denis.ecommerce.product.entity.Product;
import com.denis.ecommerce.product.entity.ProductPurchaseResponse;
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
                .category(Category.builder()
                        .id(request.category().getId())
                        .build())
                .build();
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {

        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
