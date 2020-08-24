package com.example.res.server.controllers;

import com.example.res.server.dto.ProductDto;
import com.example.res.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("api/v1")
public class PrivateProductController {
    @Autowired
    private ProductService productService;

    @PutMapping("products/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("productId") UUID productId,
                                                 @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.updateProduct(productId, productDto));
    }

    @DeleteMapping("products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") UUID productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
