package com.desafio.product.service;

import com.desafio.product.dto.response.ProductDtoResponse;
import com.desafio.product.mapper.ProductMapper;
import com.desafio.product.model.Product;
import com.desafio.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Test
    @DisplayName("Deve retornar todos os produtros cadastrados")
    void findAllProducts() {
        Product produto1 = new Product();
        produto1.setName("Produto de Teste 1");
        produto1.setPrice(10.99);
        produto1.setQuantity(5L);

        Product produto2 = new Product();
        produto2.setName("Produto de Teste 2");
        produto2.setPrice(20.99);
        produto2.setQuantity(5L);

        ProductDtoResponse dto1 = new ProductDtoResponse(produto1.getId(), produto1.getName(), produto1.getPrice(), produto1.getQuantity());
        ProductDtoResponse dto2 = new ProductDtoResponse(produto2.getId(), produto2.getName(), produto2.getPrice(), produto2.getQuantity());

        List<Product> produtosMockados = List.of(produto1, produto2);

        when(productRepository.findAll()).thenReturn(produtosMockados);
        when(productMapper.toDto(produto1)).thenReturn(dto1);
        when(productMapper.toDto(produto2)).thenReturn(dto2);

        List<ProductDtoResponse> resultado = productService.buscarProdutos();

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertTrue(resultado.contains(dto1));
        Assertions.assertTrue(resultado.contains(dto2));
    }

}