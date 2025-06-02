package com.desafio.product.controller;

import com.desafio.product.controller.dto.ProductDto;
import com.desafio.product.controller.dto.ProductDtoResponse;
import com.desafio.product.controller.dto.StockUpdateDto;
import com.desafio.product.controller.mapper.ProductMapper;
import com.desafio.product.model.Product;
import com.desafio.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

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
        return ResponseEntity.ok(produtos);
    }

    @PatchMapping("/stock/{id}")
    public ResponseEntity<ProductDtoResponse> inserirEstoque(@PathVariable String id,
                                                             @RequestBody @Valid StockUpdateDto stockUpdateDto
                                                             ) {
        Product productDtoResponse = productService.inserirEstoque(id,stockUpdateDto);
        return ResponseEntity.ok(ProductMapper.toDto(productDtoResponse));
    }
}
