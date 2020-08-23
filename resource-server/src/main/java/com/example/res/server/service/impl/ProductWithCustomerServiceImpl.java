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

import java.util.List;
import java.util.Optional;
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
    public Optional<Product> addProductByCustomer(UUID customerId, Product product) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            product.setCustomer(customer.get());
            return Optional.of(productRepository.save(product));
        } else {
            return Optional.empty();
        }
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
