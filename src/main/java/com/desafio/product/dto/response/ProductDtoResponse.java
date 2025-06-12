package com.desafio.product.dto.response;

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
