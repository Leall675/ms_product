package com.desafio.product.service;

import com.desafio.product.dto.request.ProductDto;
import com.desafio.product.dto.response.ProductDtoResponse;
import com.desafio.product.dto.request.StockUpdateDto;
import com.desafio.product.mapper.ProductMapper;
import com.desafio.product.exceptions.ProdutoDuplicado;
import com.desafio.product.exceptions.ProdutoNaoEncontrado;
import com.desafio.product.model.Product;
import com.desafio.product.repository.ProductRepository;
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

    public ProductDtoResponse salvar(ProductDto dto) {
        Product product = ProductMapper.toEntity(dto);
        validarProduto(dto.getName());
        if (product.getQuantity() == null) {
            product.setQuantity(0L);
        }
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    public ProductDtoResponse buscarPorId(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProdutoNaoEncontrado("Produto não localizado na base de dados.")
        );
        return ProductMapper.toDto(product);
    }

    public List<ProductDtoResponse> buscarProdutos() {
        List<Product> produtos = productRepository.findAll();
        return produtos.stream()
                .map(product -> ProductMapper.toDto(product))
                .collect(Collectors.toList());
    }

    public ProductDtoResponse atualizarProduto(String id, ProductDto dto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProdutoNaoEncontrado("Produto não localizado na base de dados.")
        );
        validarProduto(dto.getName());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        Product updateProduct = productRepository.save(product);
        return ProductMapper.toDto(updateProduct);
    }

    public Product inserirEstoque(String productId, StockUpdateDto stockUpdateDto) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProdutoNaoEncontrado("Produto não localizado na base de dados.")
        );

        product.setQuantity(product.getQuantity() + stockUpdateDto.getQuantity());
        return productRepository.save(product);
    }

    private void validarProduto(String name) {
        if (existeProdutoPorNome(name)){
            throw new ProdutoDuplicado("Esse produto já existe na base de dados.");
        }
    }

    private boolean existeProdutoPorNome(String product) {
        return productRepository.existsByNameIgnoreCase(product);
    }

    public void deletarProduto(String id) {
        Product produto = productRepository.findById(id).orElseThrow(
                () -> new ProdutoNaoEncontrado("Produto não localizado na base de dados."));
        productRepository.deleteById(id);
    }
}
