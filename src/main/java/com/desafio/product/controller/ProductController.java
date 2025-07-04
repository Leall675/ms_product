package com.desafio.product.controller;

import com.desafio.product.dto.request.ProductDto;
import com.desafio.product.dto.response.ProductDtoResponse;
import com.desafio.product.dto.request.StockUpdateDto;
import com.desafio.product.mapper.ProductMapper;
import com.desafio.product.model.Product;
import com.desafio.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductDtoResponse> salvar(@RequestBody @Valid ProductDto dto) {
        ProductDtoResponse responseDto =  productService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDtoResponse> produtoPorId(@PathVariable String id) {
        ProductDtoResponse productResponse = productService.buscarPorId(id);
        return ResponseEntity.ok().body(productResponse);
    }

    @GetMapping
    public ResponseEntity<List<ProductDtoResponse>> listarTodos() {
        List<ProductDtoResponse> produtos = productService.buscarProdutos();
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDtoResponse> inserirEstoque(@PathVariable String id,
                                                             @RequestBody @Valid StockUpdateDto stockUpdateDto
                                                             ) {
        Product productDtoResponse = productService.inserirEstoque(id,stockUpdateDto);
        return ResponseEntity.ok(productMapper.toDto(productDtoResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDtoResponse> alterarProduto(@PathVariable String id,
                                                             @RequestBody @Valid ProductDto dto
                                                             ) {
        ProductDtoResponse product = productService.atualizarProduto(id, dto);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable String id) {
        productService.deletarProduto(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
