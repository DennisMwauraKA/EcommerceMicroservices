package com.denis.ecommerce.product.service;

import com.denis.ecommerce.product.entity.*;
import com.denis.ecommerce.product.exception.ProductPurchaseException;
import com.denis.ecommerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    // create a product
    public Integer createProduct(ProductRequest request) {
        // find if a product exists in the database and create it
        try {
            if (productRepository.existsById(request.id())) {
                throw new RuntimeException("Product already exists");
            }

            var product = productMapper.toProduct(request);
            productRepository.save(product);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return request.id();
    }

    // find a product using ID
    public Product findProductById(Integer productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
    }


    // delete product using id
    public Integer deleteProduct(Integer productId) {
        //check if product exists and  delete it
        try {
            if (productRepository.existsById(productId)) {
                productRepository.deleteById(productId);
            }
            return productId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //update a product
    public Integer updateProduct(Integer productId, UpdateDtoRequest request) {
        // check if the product exists in the database
        try {
            Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
            // only update a product attribute that need to be updated
            if (request.getName() != null) {
                existingProduct.setName(request.getName());
            }
            if (request.getDescription() != null) {
                existingProduct.setDescription(request.getDescription());
            }
            if (request.getPrice() != null) {
                existingProduct.setPrice(request.getPrice());

            }
            if (request.getCategory() != null) {
                existingProduct.setCategory(request.getCategory());
            }

            productRepository.save(existingProduct);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return request.getId();


    }

    // get the purchased products
    public List<ProductPurchaseResponse> purchasedProducts(List<ProductPurchaseRequest> request) {
        // get the products with their ids
        var productIds = request.stream().map(ProductPurchaseRequest::id).toList();


        //find  the stored products in the database and order with the ids
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products do not exist");
        }

        var storedRequest = request.stream().sorted(Comparator.comparing(ProductPurchaseRequest::id)).toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("insufficient quantity of product with ID:" + productRequest.id());
            }
            //update the product quantity in the database after a purchase
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchasedProducts.add(productMapper.toProductPurchaseResponse(product,productRequest.quantity()));
        }

        return purchasedProducts;
    }
}
