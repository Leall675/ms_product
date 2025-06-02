package com.desafio.product.controller.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockUpdateDto {
    @Positive(message = "A quantidade deve ser maior que 0.")
    private long quantity;
}