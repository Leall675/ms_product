package com.desafio.product.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDtoResponse {
    String id;
    String name;
    Double price;
    long quantity;

}
