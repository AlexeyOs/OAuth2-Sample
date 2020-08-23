package com.example.res.server.service.impl;

import com.example.res.server.dto.CustomerDto;
import com.example.res.server.entity.Customer;

import com.example.res.server.entity.Product;
import com.example.res.server.mapper.CustomerMapper;
import com.example.res.server.repository.CustomerRepository;
import com.example.res.server.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<Customer> findAllCustomers() {
        Iterable<Customer> customerIterable = customerRepository.findAll();
        Iterator<Customer> customerIterator = customerIterable.iterator();
        List<Customer> customerList = new ArrayList<>();
        customerIterator.forEachRemaining(customerList::add);
        return customerList;
    }

    @Override
    public CustomerDto findCustomerById(UUID customerId) {
        Customer customerEntity = customerRepository.getOne(customerId);
        return customerMapper.toDto(customerEntity);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        Customer customerEntityForDelete = customerRepository.getOne(customerId);
        customerEntityForDelete.setIsDeleted(true);
        customerRepository.save(customerEntityForDelete);
    }
}
