package com.desafio.product.dto.request;

import com.desafio.product.enuns.StockOperationEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockUpdateDto {
    @Positive(message = "A quantidade deve ser maior que 0.")
    @NotNull
    private long quantity;

    @NotNull(message = "Operação não pode ser nulo.")
    private StockOperationEnum operation;
}