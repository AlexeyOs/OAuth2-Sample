package com.example.res.server.service;

import com.example.res.server.dto.CustomerDto;
import com.example.res.server.entity.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> findAllCustomers();
    CustomerDto findCustomerById(UUID customerId);
    Customer addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(UUID customerId);
}
