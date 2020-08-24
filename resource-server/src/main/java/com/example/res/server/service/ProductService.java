package com.example.res.server.service;

import com.example.res.server.dto.ProductDto;
import com.example.res.server.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDto findProductById(UUID productId);
    ProductDto updateProduct(UUID productId, ProductDto productDto);
    void deleteProduct(UUID productId);
}
