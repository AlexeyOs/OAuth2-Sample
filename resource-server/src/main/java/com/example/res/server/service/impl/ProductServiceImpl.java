package com.example.res.server.service.impl;

import com.example.res.server.dto.ProductDto;
import com.example.res.server.entity.Product;
import com.example.res.server.mapper.ProductMapper;
import com.example.res.server.repository.ProductRepository;
import com.example.res.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findAllProducts() {
        Iterable<Product> productsIterable = productRepository.findAll();
        Iterator<Product> productIterator = productsIterable.iterator();
        List<Product> productList = new ArrayList<>();
        productIterator.forEachRemaining(productList::add);
        return productList;
    }

    @Override
    public ProductDto findProductById(UUID productId) {
        Product productEntity = productRepository.getOne(productId);
        return productMapper.toDto(productEntity);
    }

    @Transactional
    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void deleteProduct(UUID productId) {
        Product productEntityForDelete = productRepository.getOne(productId);
        productEntityForDelete.setIsDeleted(true);
        productRepository.save(productEntityForDelete);
    }
}
