package com.example.res.server.service.impl;

import com.example.res.server.dto.ProductDto;
import com.example.res.server.entity.Customer;
import com.example.res.server.entity.Product;
import com.example.res.server.mapper.ProductMapper;
import com.example.res.server.repository.CustomerRepository;
import com.example.res.server.repository.ProductRepository;
import com.example.res.server.service.ProductWithCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductWithCustomerServiceImpl implements ProductWithCustomerService {

    @Autowired()
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductDto addProductByCustomer(UUID customerId, ProductDto productDto) {
        Customer customerEntity = customerRepository.getOne(customerId);
        Product productEntity = productMapper.toEntity(productDto);
        productEntity.setCustomer(customerEntity);
        productEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        productEntity.setIsDeleted(false);
        Product resultProductEntity = productRepository.save(productEntity);
        return productMapper.toDto(resultProductEntity);
    }

    @Override
    public List<ProductDto> getProductsByCustomer(UUID customerId) {
        Customer customer = customerRepository.getOne(customerId);
        List<Product> productEntityList = customer.getProductList();
        return productEntityList
                .stream()
                .map(product -> productMapper.toDto(product))
                .collect(Collectors.toList());
    }
}
