package com.desafio.product.exceptions;

import org.jetbrains.annotations.NotNull;

public class ProdutoNaoEncontrado extends RuntimeException {
    public ProdutoNaoEncontrado(String message) {
        super(message);
    }
}
