package com.desafio.product.exceptions;

import com.desafio.product.controller.dto.error.ErroCampo;
import com.desafio.product.controller.dto.error.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErroCampo> erro = ex.getFieldErrors()
                .stream()
                .map(fieldError -> new ErroCampo(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        ErroResposta resposta = new ErroResposta();
        resposta.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        resposta.setMessage("Erro de validação.");
        resposta.setErros(erro);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resposta);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErroResposta resposta = new ErroResposta();
        resposta.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        resposta.setMessage("Erro de validação: verifique os tipos de dados enviados.");
        resposta.setErros(List.of());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(resposta);
    }

    @ExceptionHandler(ProdutoDuplicado.class)
    public ResponseEntity<ErroResposta> handleProdutoDuplicado(ProdutoDuplicado ex) {
        ErroResposta resposta = new ErroResposta();
        resposta.setStatus(HttpStatus.CONFLICT.value());
        resposta.setMessage(ex.getMessage());
        resposta.setErros(List.of());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
    }

    @ExceptionHandler(ProdutoNaoEncontrado.class)
    public ResponseEntity<ErroResposta> handleProdutoNaoEncontrado(ProdutoNaoEncontrado ex) {
        ErroResposta resposta = new ErroResposta();
        resposta.setStatus(HttpStatus.NOT_FOUND.value());
        resposta.setMessage(ex.getMessage());
        resposta.setErros(List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }
}
