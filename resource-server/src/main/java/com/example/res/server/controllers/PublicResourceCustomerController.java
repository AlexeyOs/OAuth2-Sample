package com.example.res.server.controllers;

import java.util.List;
import java.util.UUID;

import com.example.res.server.dto.CustomerDto;
import com.example.res.server.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("api/v1")
public class PublicResourceCustomerController {
	@Autowired
	private CustomerService customerService;

	@GetMapping("customers/{customerId}")
	public HttpEntity<CustomerDto> getCustomer(@PathVariable("customerId") UUID id) {
		return ResponseEntity.ok(customerService.findCustomerById(id));
	}

	@GetMapping("customers")
	public ResponseEntity<List<CustomerDto>> getAllCustomers() {
		return ResponseEntity.ok(customerService.findAllCustomers());
	}

	@PostMapping("customers")
	public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto) {
		return ResponseEntity.ok(customerService.addCustomer(customerDto));
	}

} 