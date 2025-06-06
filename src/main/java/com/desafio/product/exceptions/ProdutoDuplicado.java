package com.desafio.product.exceptions;

public class ProdutoDuplicado extends RuntimeException {
    public ProdutoDuplicado(String message) {
        super(message);
    }
}
