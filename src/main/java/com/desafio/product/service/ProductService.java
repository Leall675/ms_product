package com.desafio.product.service;

import com.desafio.product.controller.dto.ProductDto;
import com.desafio.product.controller.dto.ProductDtoResponse;
import com.desafio.product.controller.dto.StockUpdateDto;
import com.desafio.product.controller.mapper.ProductMapper;
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
        if (product.getQuantity() == null) {
            product.setQuantity(0L);
        }
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    public ProductDtoResponse buscarPorId(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("Produto não localizado.")
        );
        return ProductMapper.toDto(product);
    }

    public List<ProductDtoResponse> buscarProdutos() {
        List<Product> produtos = productRepository.findAll();
        return produtos.stream()
                .map(product -> ProductMapper.toDto(product))
                .collect(Collectors.toList());
    }

    public Product inserirEstoque(String productId, StockUpdateDto stockUpdateDto) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("Produto não localizado.")
        );
        product.setQuantity(product.getQuantity() + stockUpdateDto.getQuantity());
        return productRepository.save(product);
    }
}
