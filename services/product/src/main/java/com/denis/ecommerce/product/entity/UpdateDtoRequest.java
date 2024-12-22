package com.denis.ecommerce.product.entity;

import com.denis.ecommerce.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDtoRequest {
    private Integer id;
    private String name;
   private String description;
  private  double availableQuantity;
   private BigDecimal price;
  private  Category category;
}
