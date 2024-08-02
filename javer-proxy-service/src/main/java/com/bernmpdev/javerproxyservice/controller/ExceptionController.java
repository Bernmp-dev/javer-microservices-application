package com.bernmpdev.javerproxyservice.controller;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ResponseBuilder> handleFeignException(FeignException ex) {

        HttpStatus status = HttpStatus.resolve(ex.status());
        List<String> messages = new ArrayList<>();
        String errorMessage = ex.contentUTF8();

        if (status == null) status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (status == HttpStatus.BAD_REQUEST && errorMessage.startsWith("{")) {
            messages = parseErrorMessage(errorMessage);
        } else {
            messages.add(errorMessage);
        }

        return ResponseEntity
                .status(status)
                .body(new ResponseBuilder(status, messages)
                );
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

    private List<String> parseErrorMessage(String errorMessage) {
        List<String> messages = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> errors = objectMapper.readValue(errorMessage, HashMap.class);
            errors.forEach((element, message) -> messages.add(message));
        } catch (IOException e) {
            messages.add(errorMessage);
        }
        return messages;
    }
}