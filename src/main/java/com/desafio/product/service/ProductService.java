package com.desafio.product.service;

import com.desafio.product.dto.request.ProductDto;
import com.desafio.product.dto.response.ProductDtoResponse;
import com.desafio.product.dto.request.StockUpdateDto;
import com.desafio.product.mapper.ProductMapper;
import com.desafio.product.exceptions.ProdutoDuplicado;
import com.desafio.product.exceptions.ProdutoNaoEncontrado;
import com.desafio.product.model.Product;
import com.desafio.product.repository.ProductRepository;
import com.desafio.product.validation.ProductValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductValidation validation;

    public ProductDtoResponse salvar(ProductDto dto) {
        Product product = productMapper.toEntity(dto);
        validation.validarProduto(dto.getName());
        if (product.getQuantity() == null) {
            product.setQuantity(0L);
        }
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    public ProductDtoResponse buscarPorId(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProdutoNaoEncontrado("Produto não localizado na base de dados.")
        );
        return productMapper.toDto(product);
    }

    public List<ProductDtoResponse> buscarProdutos() {
        List<Product> produtos = productRepository.findAll();
        return produtos.stream()
                .map(product -> productMapper.toDto(product))
                .collect(Collectors.toList());
    }

    public ProductDtoResponse atualizarProduto(String id, ProductDto dto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProdutoNaoEncontrado("Produto não localizado na base de dados.")
        );
        validation.validarProduto(dto.getName());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        Product updateProduct = productRepository.save(product);
        return productMapper.toDto(updateProduct);
    }

    public Product inserirEstoque(String productId, StockUpdateDto stockUpdateDto) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProdutoNaoEncontrado("Produto não localizado na base de dados.")
        );

        product.setQuantity(product.getQuantity() + stockUpdateDto.getQuantity());
        return productRepository.save(product);
    }

    public void deletarProduto(String id) {
        Product produto = productRepository.findById(id).orElseThrow(
                () -> new ProdutoNaoEncontrado("Produto não localizado na base de dados."));
        productRepository.deleteById(id);
    }
}
