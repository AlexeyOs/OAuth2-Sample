package com.example.res.server.controllers;

import com.example.res.server.dto.ProductDto;
import com.example.res.server.service.CustomerService;
import com.example.res.server.service.ProductService;
import com.example.res.server.service.ProductWithCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/v1")
public class PublicResourceProductController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductWithCustomerService productWithCustomerService;

    @GetMapping("customers/{customerId}/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(@PathVariable("customerId") UUID customerId) {
        return ResponseEntity.ok(productWithCustomerService.getProductsByCustomer(customerId));
    }

    @PostMapping("customers/{customerId}/products")
    public ResponseEntity<ProductDto> addProduct(@PathVariable("customerId") UUID customerId,
                                            @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productWithCustomerService
                .addProductByCustomer(customerId, productDto));
    }

    @GetMapping("products/{productId}")
    public HttpEntity<ProductDto> getProductById(@PathVariable("productId") UUID productId) {
        return ResponseEntity.ok(productService.findProductById(productId));
    }
}
