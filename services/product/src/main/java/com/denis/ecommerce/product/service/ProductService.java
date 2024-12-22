package com.denis.ecommerce.product.service;

import com.denis.ecommerce.product.entity.Product;
import com.denis.ecommerce.product.entity.ProductRequest;
import com.denis.ecommerce.product.entity.UpdateDtoRequest;
import com.denis.ecommerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

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
      try{
          if (productRepository.existsById(request.id())) {
              throw new RuntimeException("Product already exists");
          }

          Product product = productMapper.toProduct(request);
          productRepository.save(product);

      } catch (RuntimeException e) {
          throw new RuntimeException(e);
      }
        return request.id();
    }

    // find a product using ID
    public Product findProductById(Integer productId) {
        return productRepository.findById(productId).orElseThrow(()-> new RuntimeException("Product not found"));
    }


    // delete product using id
    public Integer deleteProduct(Integer productId) {
        //check if product exists and  delete it
        try{
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
            Product existingProduct = productRepository.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));
            // only update a product attribute that need to be updated
            if (request.getName()!=null) {
                existingProduct.setName(request.getName());
            }
            if (request.getDescription()!=null) {
                existingProduct.setDescription(request.getDescription());
            }
            if (request.getPrice()!=null) {
                existingProduct.setPrice(request.getPrice());

            }
            if (request.getCategory()!=null) {
                existingProduct.setCategory(request.getCategory());
            }

            productRepository.save(existingProduct);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return request.getId();


    }
}
