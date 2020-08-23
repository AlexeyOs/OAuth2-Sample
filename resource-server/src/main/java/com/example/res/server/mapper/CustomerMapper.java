package com.example.res.server.mapper;

import com.example.res.server.dto.CustomerDto;
import com.example.res.server.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CustomerMapper extends AbstractMapper<Customer, CustomerDto> {
    @Autowired
    public CustomerMapper() {
        super(Customer.class, CustomerDto.class);
    }

}
