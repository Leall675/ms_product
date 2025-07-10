package com.desafio.product.exceptions;

public class ProdutoSemEstoque extends RuntimeException {
    public ProdutoSemEstoque(String message) {
        super(message);
    }
}
