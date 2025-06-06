package com.desafio.product.exceptions;

import com.desafio.product.controller.dto.error.ErroCampo;
import com.desafio.product.controller.dto.error.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
