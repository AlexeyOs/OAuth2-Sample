package com.example.res.server.service;

import com.example.res.server.dto.ProductDto;
import com.example.res.server.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> findAllProducts();
    ProductDto findProductById(UUID productId);
    void updateProduct(Product product);
    void deleteProduct(UUID productId);
}
