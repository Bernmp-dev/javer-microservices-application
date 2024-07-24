package com.bernmpdev.javerproxyservice.controller;

import feign.FeignException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ResponseBuilder> handleFeignException(FeignException ex) {
        HttpStatus status = HttpStatus.resolve(ex.status());

        if (status == null) status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status)
                .body(new ResponseBuilder(status, ex.contentUTF8())
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBuilder> handleGenericException(Exception ex) {

        return ResponseEntity
                .internalServerError()
                .body(new ResponseBuilder(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ex.getMessage())
                );
    }

    @Data
    public static class ResponseBuilder {
        private HttpStatus status;
        private String message;

        public ResponseBuilder(HttpStatus status, String message) {
            this.status = status;
            this.message = message;
        }

    }
}
