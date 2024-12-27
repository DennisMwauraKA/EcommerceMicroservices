package com.denis.ecommerce.product.controller;


import com.denis.ecommerce.product.entity.*;
import com.denis.ecommerce.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    // Create a product
    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody ProductRequest request) {

        return ResponseEntity.ok(productService.createProduct(request));

    }

    // get product using the id
    @GetMapping("/getProduct/{product-id}")
    public ResponseEntity<Product> findProductById(@PathVariable("product-id") Integer productId) {
        return ResponseEntity.ok(productService.findProductById(productId));
    }


    //Delete a product with the Id
    @DeleteMapping("/deleteProduct/{product-id}")
    public ResponseEntity<Integer> deleteProduct(@PathVariable("product-id") Integer productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }


    // update a product
    @PutMapping("/updateProduct/{product-id}")
    public ResponseEntity<Integer> updateProduct(@PathVariable("product-id") Integer productId, @RequestBody UpdateDtoRequest request) {
        return ResponseEntity.ok(productService.updateProduct(productId, request));
    }

    // get purchased products
    @GetMapping("/purchased")
    public ResponseEntity<List<ProductPurchaseResponse>>purchasedProducts(
        @RequestBody  List<ProductPurchaseRequest> request
    ){
        return ResponseEntity.ok(productService.purchasedProducts(request));
    }

}
