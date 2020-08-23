package com.example.res.server.service.impl;

import com.example.res.server.dto.CustomerDto;
import com.example.res.server.entity.Customer;

import com.example.res.server.mapper.CustomerMapper;
import com.example.res.server.repository.CustomerRepository;
import com.example.res.server.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<CustomerDto> findAllCustomers() {
        List<Customer> customerEntityList = customerRepository.findAll();
        return customerEntityList
                .stream()
                .map(customer -> customerMapper.toDto(customer))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto findCustomerById(UUID customerId) {
        Customer customerEntity = customerRepository.getOne(customerId);
        return customerMapper.toDto(customerEntity);
    }

    @Transactional
    @Override
    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customerEntity = customerMapper.toEntity(customerDto);
        customerEntity.setIsDeleted(false);
        customerEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return customerMapper.toDto(customerRepository.save(customerEntity));
    }

    @Transactional
    @Override
    public CustomerDto updateCustomer(UUID customerId, CustomerDto customerDto) {
        Customer customerForUpdate = customerRepository.getOne(customerId);
        customerForUpdate.setTitle(customerDto.getTitle());
        Customer resultEntity = customerRepository.save(customerForUpdate);
        return customerMapper.toDto(resultEntity);
    }

    @Transactional
    @Override
    public void deleteCustomer(UUID customerId) {
        Customer customerEntityForDelete = customerRepository.getOne(customerId);
        customerEntityForDelete.setIsDeleted(true);
        customerRepository.save(customerEntityForDelete);
    }
}
