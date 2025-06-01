package com.desafio.product.controller.dto;

import com.desafio.product.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    @NotBlank(message = "Name não pode ser nulo ou vazio.")
    String name;
    @NotNull(message = "Preço não pode ser nulo.")
    @Positive(message = "Valor deve ser maior que 0.")
    Double price;
    @Positive(message = "Quantidade de estoque deve ser maior que 0.")
    long quantity;
}
