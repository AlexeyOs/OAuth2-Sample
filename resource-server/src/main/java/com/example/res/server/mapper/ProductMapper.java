package com.example.res.server.mapper;

import com.example.res.server.dto.ProductDto;
import com.example.res.server.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends AbstractMapper<Product, ProductDto> {

    @Autowired
    public ProductMapper() {
        super(Product.class, ProductDto.class);
    }
}
