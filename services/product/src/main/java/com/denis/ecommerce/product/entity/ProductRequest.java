package com.denis.ecommerce.product.entity;

import com.denis.ecommerce.category.entity.Category;

import java.math.BigDecimal;

public record ProductRequest(
        Integer id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price,
        Category category

) {
}
