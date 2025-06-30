package com.desafio.product.controller;

import com.desafio.product.dto.request.ProductDto;
import com.desafio.product.dto.response.ProductDtoResponse;
import com.desafio.product.dto.request.StockUpdateDto;
import com.desafio.product.mapper.ProductMapper;
import com.desafio.product.model.Product;
import com.desafio.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Dado que realizo a criação de um produto, então o produto deve ser salvo com sucesso.")
    void testSalvar() {
        ProductDto dtoRequest = new ProductDto();
        dtoRequest.setName("Produto Teste");
        dtoRequest.setPrice(50.0);

        ProductDtoResponse dtoResponse = new ProductDtoResponse("123", "Produto Teste", 50.0, 0L);

        when(productService.salvar(dtoRequest)).thenReturn(dtoResponse);

        ResponseEntity<ProductDtoResponse> response = productController.salvar(dtoRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dtoResponse, response.getBody());
    }

    @Test
    @DisplayName("Dado que desejo buscar um produto por ID, então deve retornar o produto correspondente.")
    void testProdutoPorId() {
        ProductDtoResponse dtoResponse = new ProductDtoResponse("123", "Produto Teste", 50.0, 0L);

        when(productService.buscarPorId("123")).thenReturn(dtoResponse);

        ResponseEntity<ProductDtoResponse> response = productController.produtoPorId("123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtoResponse, response.getBody());
    }

    @Test
    @DisplayName("Dado que desejo listar todos os produtos, deve retornar a lista de produtos.")
    void testListarTodos() {
        ProductDtoResponse product1 = new ProductDtoResponse("1", "Produto 1", 10.0, 5L);
        ProductDtoResponse product2 = new ProductDtoResponse("2", "Produto 2", 20.0, 10L);

        List<ProductDtoResponse> responseList = Arrays.asList(product1, product2);

        when(productService.buscarProdutos()).thenReturn(responseList);

        ResponseEntity<List<ProductDtoResponse>> response = productController.listarTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @DisplayName("Dado que desejo inserir estoque, deve retornar o produto atualizado.")
    void testInserirEstoque() {
        StockUpdateDto stockUpdateDto = new StockUpdateDto();
        stockUpdateDto.setQuantity(10L);

        Product product = new Product();
        product.setId("123");
        product.setName("Produto Teste");
        product.setPrice(50.0);
        product.setQuantity(10L);

        ProductDtoResponse dtoResponse = new ProductDtoResponse("123", "Produto Teste", 50.0, 20L);

        when(productService.inserirEstoque("123", stockUpdateDto)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(dtoResponse);

        ResponseEntity<ProductDtoResponse> response = productController.inserirEstoque("123", stockUpdateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(dtoResponse.getId(), response.getBody().getId());
        assertEquals(dtoResponse.getName(), response.getBody().getName());
        assertEquals(dtoResponse.getPrice(), response.getBody().getPrice());
        assertEquals(20L, response.getBody().getQuantity());
    }

    @Test
    @DisplayName("Dado que desejo alterar um produto, então deve retornar o produto atualizado.")
    void testAlterarProduto() {
        ProductDto dtoRequest = new ProductDto();
        dtoRequest.setName("Produto Atualizado");
        dtoRequest.setPrice(60.0);

        ProductDtoResponse dtoResponse = new ProductDtoResponse("123", "Produto Atualizado", 60.0, 0L);

        when(productService.atualizarProduto("123", dtoRequest)).thenReturn(dtoResponse);

        ResponseEntity<ProductDtoResponse> response = productController.alterarProduto("123", dtoRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dtoResponse, response.getBody());
    }

    @Test
    @DisplayName("Dado que desejo excluir um produto, deve retornar status OK.")
    void testDeletarProduto() {
        String productId = "123";

        ResponseEntity<Void> response = productController.deletarProduto(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}