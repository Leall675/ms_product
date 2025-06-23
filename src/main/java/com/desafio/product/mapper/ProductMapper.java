package com.desafio.product.mapper;

import com.desafio.product.dto.request.ProductDto;
import com.desafio.product.dto.response.ProductDtoResponse;
import com.desafio.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDtoResponse toDto(Product product) {
        ProductDtoResponse dto = new ProductDtoResponse();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        return dto;
    }

    public Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        return product;
    }
}
