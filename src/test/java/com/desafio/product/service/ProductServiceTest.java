package com.desafio.product.service;

import com.desafio.product.dto.request.ProductDto;
import com.desafio.product.dto.response.ProductDtoResponse;
import com.desafio.product.dto.request.StockUpdateDto;
import com.desafio.product.exceptions.ProdutoDuplicado;
import com.desafio.product.exceptions.ProdutoNaoEncontrado;
import com.desafio.product.mapper.ProductMapper;
import com.desafio.product.model.Product;
import com.desafio.product.repository.ProductRepository;
import com.desafio.product.validation.ProductValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductValidation productValidation;

    private ProductDto dtoRequest;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        dtoRequest = new ProductDto();
        dtoRequest.setName("Produto Teste");
        dtoRequest.setPrice(50.0);

        product = new Product();
        product.setId("1");
        product.setName(dtoRequest.getName());
        product.setPrice(dtoRequest.getPrice());
        product.setQuantity(10L);
    }

    @Test
    @DisplayName("Dado que realizo a criação de um produto, então o produto deve ser salvo com sucesso.")
    void salvar() {
        when(productMapper.toEntity(dtoRequest)).thenReturn(product);
        when(productValidation.existeProdutoPorNome(dtoRequest.getName())).thenReturn(false);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(new ProductDtoResponse(product.getId(), product.getName(), product.getPrice(), product.getQuantity()));

        ProductDtoResponse response = productService.salvar(dtoRequest);

        assertNotNull(response);
        assertEquals(dtoRequest.getName(), response.getName());
        assertEquals(dtoRequest.getPrice(), response.getPrice());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Dado que o nome do produto já existe, então deve lançar uma exceção de ProdutoDuplicado.")
    void salvarProdutoDuplicado() {
        doThrow(new ProdutoDuplicado("Esse produto já existe na base de dados."))
                .when(productValidation)
                .validarProduto(dtoRequest.getName());
        assertThrows(ProdutoDuplicado.class, () -> productService.salvar(dtoRequest));
        verify(productValidation).validarProduto(dtoRequest.getName());
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Dado que desejo buscar um produto por ID, então deve retornar o produto correspondente.")
    void buscarPorId() {
        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(new ProductDtoResponse(product.getId(), product.getName(), product.getPrice(), product.getQuantity()));

        ProductDtoResponse response = productService.buscarPorId("1");

        assertNotNull(response);
        assertEquals("1", response.getId());
        verify(productRepository).findById("1");
    }

    @Test
    @DisplayName("Dado que busco um produto com ID inválido, deve lançar uma exceção ProdutoNaoEncontrado.")
    void buscarPorIdProdutoNaoEncontrado() {
        when(productRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontrado.class, () -> productService.buscarPorId("1"));
    }

    @Test
    @DisplayName("Dado que desejo listar todos os produtos, deve retornar a lista com todos os produtos.")
    void buscarProdutos() {
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Produto 1");
        product1.setPrice(10.0);
        product1.setQuantity(5L);

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Produto 2");
        product2.setPrice(20.0);
        product2.setQuantity(10L);

        List<Product> productList = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.toDto(product1)).thenReturn(new ProductDtoResponse(product1.getId(), product1.getName(), product1.getPrice(), product1.getQuantity()));
        when(productMapper.toDto(product2)).thenReturn(new ProductDtoResponse(product2.getId(), product2.getName(), product2.getPrice(), product2.getQuantity()));

        List<ProductDtoResponse> responseList = productService.buscarProdutos();

        assertEquals(2, responseList.size());
        verify(productRepository).findAll();
    }

    @Test
    @DisplayName("Dado que desejo atualizar um produto, então o produto deve ser atualizado com sucesso.")
    void atualizarProduto() {
        ProductDto updateDto = new ProductDto();
        updateDto.setName("Produto Atualizado");
        updateDto.setPrice(60.0);

        when(productRepository.findById("1")).thenReturn(Optional.of(product));

        product.setName(updateDto.getName());
        product.setPrice(updateDto.getPrice());

        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(new ProductDtoResponse(product.getId(), product.getName(), product.getPrice(), product.getQuantity()));

        ProductDtoResponse response = productService.atualizarProduto("1", updateDto);

        assertNotNull(response);
        assertEquals("Produto Atualizado", response.getName());
        assertEquals(60.0, response.getPrice());
        verify(productRepository).findById("1");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Dado que desejo inserir estoque para um produto, Entao o valor do campo que representa a quantidade deve ser atualizado.")
    void inserirEstoqueParaOProduto() {
        StockUpdateDto dtoStock = new StockUpdateDto();
        dtoStock.setQuantity(10L);
        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        Product updatedProduct = productService.manipularEstoque("1", dtoStock);
        assertNotNull(updatedProduct);
        assertEquals(20, updatedProduct.getQuantity());
        verify(productRepository).findById("1");
        verify(productRepository).save(product);
    }

    @Test
    @DisplayName("Dado que desejo atualizar um produto com ID inválido, deve lançar uma exceção ProdutoNaoEncontrado.")
    void atualizarProdutoProdutoNaoEncontrado() {
        ProductDto updateDto = new ProductDto();
        updateDto.setName("Produto Atualizado");
        updateDto.setPrice(60.0);

        when(productRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontrado.class, () -> productService.atualizarProduto("1", updateDto));
    }

    @Test
    @DisplayName("Dado que desejo excluir um produto, então o produto deve ser removido com sucesso.")
    void deletarProduto() {
        when(productRepository.findById("1")).thenReturn(Optional.of(product));

        productService.deletarProduto("1");

        verify(productRepository).deleteById("1");
    }

    @Test
    @DisplayName("Dado que desejo excluir um produto com ID inválido, deve lançar uma exceção ProdutoNaoEncontrado.")
    void deletarProdutoProdutoNaoEncontrado() {
        when(productRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontrado.class, () -> productService.deletarProduto("1"));
    }
}