package com.example.res.server.mapper;

import com.example.res.server.dto.ProductDto;
import com.example.res.server.entity.Product;
import com.example.res.server.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.UUID;

@Component
public class ProductMapper extends AbstractMapper<Product, ProductDto> {

    private final ModelMapper mapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public ProductMapper(ModelMapper mapper, CustomerRepository customerRepository) {
        super(Product.class, ProductDto.class);
        this.mapper = mapper;
        this.customerRepository = customerRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Product.class, ProductDto.class)
                .addMappings(m -> m.skip(ProductDto::setCustomerId)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(ProductDto.class, Product.class)
                .addMappings(m -> m.skip(Product::setCustomer)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Product source, ProductDto destination) {
        destination.setCustomerId(getId(source));
    }

    private UUID getId(Product source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getCustomer().getId();
    }

    @Override
    void mapSpecificFields(ProductDto source, Product destination) {
        destination.setCustomer(customerRepository.findById(source.getCustomerId()).orElse(null));
    }
}
