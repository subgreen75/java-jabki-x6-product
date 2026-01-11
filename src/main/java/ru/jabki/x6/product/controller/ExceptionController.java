package ru.jabki.x6.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.jabki.x6.product.model.ApiError;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllException(final Exception badRequestException) {
        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                false,
                                badRequestException.getMessage()
                        )
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String errorMessage = "Ошибка парсинга JSON. Пожалуйста проверьте синтаксис JSON  или типы данных." + ex.getMessage();
        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                false,
                                errorMessage
                        )
                );
    }
}