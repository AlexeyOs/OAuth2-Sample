package com.example.res.server.service;

import com.example.res.server.dto.CustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDto> findAllCustomers();
    CustomerDto findCustomerById(UUID customerId);
    CustomerDto addCustomer(CustomerDto customerDto);
    CustomerDto updateCustomer(UUID customerId, CustomerDto customer);
    void deleteCustomer(UUID customerId);
}
