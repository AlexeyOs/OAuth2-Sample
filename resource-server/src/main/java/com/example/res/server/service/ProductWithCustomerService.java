package com.example.res.server.service;

import com.example.res.server.dto.ProductDto;
import com.example.res.server.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductWithCustomerService {
    Optional<Product> addProductByCustomer(UUID customerId, Product product);
    List<ProductDto> getProductsByCustomer(UUID customerId);
}
