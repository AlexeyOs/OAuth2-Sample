package com.example.res.server.service;

import com.example.res.server.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductWithCustomerService {
    ProductDto addProductByCustomer(UUID customerId, ProductDto productDto);
    List<ProductDto> getProductsByCustomer(UUID customerId);
}
