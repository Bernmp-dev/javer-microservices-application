package com.bernmpdev.javerproxyservice.controller;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignException(FeignException ex) {

        HttpStatus status = HttpStatus.resolve(ex.status());

        if (status == null) status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status)
                .body(ex.contentUTF8());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBuilder> handleGenericException(Exception ex) {
        List<String> messages = new ArrayList<>();
        messages.add(ex.getMessage());

        return ResponseEntity
                .internalServerError()
                .body(new ResponseBuilder(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        messages)
                );
    }

    public record ResponseBuilder (HttpStatus status, List<String> messages) {}
}