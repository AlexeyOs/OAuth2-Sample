package com.example.res.server.service.impl;

import com.example.res.server.dto.ProductDto;
import com.example.res.server.entity.Product;
import com.example.res.server.mapper.ProductMapper;
import com.example.res.server.repository.ProductRepository;
import com.example.res.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductDto findProductById(UUID productId) {
        Product productEntity = productRepository.getOne(productId);
        return productMapper.toDto(productEntity);
    }

    @Transactional
    @Override
    public ProductDto updateProduct(UUID productId, ProductDto productDto) {
        Product productForUpdate = productRepository.getOne(productId);
        productForUpdate.setTitle(productDto.getTitle());
        productForUpdate.setDescription(productDto.getDescription());
        productForUpdate.setPrice(productDto.getPrice());
        productForUpdate.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        Product resultEntity = productRepository.save(productForUpdate);
        return productMapper.toDto(resultEntity);
    }

    @Transactional
    @Override
    public void deleteProduct(UUID productId) {
        Product productEntityForDelete = productRepository.getOne(productId);
        productEntityForDelete.setIsDeleted(true);
        productRepository.save(productEntityForDelete);
    }
}
