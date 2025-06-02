package com.desafio.product.controller.mapper;

import com.desafio.product.controller.dto.ProductDto;
import com.desafio.product.controller.dto.ProductDtoResponse;
import com.desafio.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static ProductDtoResponse toDto(Product product) {
        ProductDtoResponse toDto = new ProductDtoResponse();
        toDto.setId(product.getId());
        toDto.setName(product.getName());
        toDto.setPrice(product.getPrice());
        toDto.setQuantity(product.getQuantity());
        return  toDto;
    }

    public static Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        return product;
    }
}
