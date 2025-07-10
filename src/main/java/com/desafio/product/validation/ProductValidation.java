package com.desafio.product.validation;

import com.desafio.product.exceptions.ProdutoDuplicado;
import com.desafio.product.exceptions.ProdutoNaoEncontrado;
import com.desafio.product.exceptions.ProdutoSemEstoque;
import com.desafio.product.model.Product;
import com.desafio.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductValidation {

    @Autowired
    private ProductRepository productRepository;

    public void validarProduto(String name) {
        if (existeProdutoPorNome(name)){
            throw new ProdutoDuplicado("Esse produto já existe na base de dados.");
        }
    }

    public boolean existeProdutoPorNome(String product) {
        return productRepository.existsByNameIgnoreCase(product);
    }

    public void validarEstoqueParaReducao(Product produto, long quantidadeSolicitada) {
        if (produto.getQuantity() < quantidadeSolicitada) {
            throw new ProdutoSemEstoque("Produto com estoque insuficiente.");
        }
    }
}
