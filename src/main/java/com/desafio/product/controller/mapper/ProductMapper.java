package com.desafio.product.controller.mapper;

import com.desafio.product.controller.dto.ProductDto;
import com.desafio.product.controller.dto.ProductDtoResponse;
import com.desafio.product.model.Product;

public class ProductMapper {

    public static ProductDtoResponse toDto(Product product) {
        ProductDtoResponse responseDto = new ProductDtoResponse();
        responseDto.setId(product.getId());
        responseDto.setName(product.getName());
        responseDto.setPrice(product.getPrice());
        responseDto.setQuantity(product.getQuantity());
        return  responseDto;
    }

    public static Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        return product;
    }
}
