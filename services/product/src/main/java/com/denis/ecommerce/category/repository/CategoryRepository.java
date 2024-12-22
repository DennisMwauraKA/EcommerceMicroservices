package com.denis.ecommerce.category.repository;

import com.denis.ecommerce.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
