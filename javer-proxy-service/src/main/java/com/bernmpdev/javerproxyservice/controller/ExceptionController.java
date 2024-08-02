package com.bernmpdev.javerproxyservice.controller;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

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
                .body(new ResponseBuilder(status, messages));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBuilder> handleGenericException(Exception ex) {
        List<String> messages = new ArrayList<>();
        messages.add(ex.getMessage());

        logger.error("Unexpected error occurred: ", ex);

        return ResponseEntity
                .internalServerError()
                .body(new ResponseBuilder(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        messages));
    }

    public static class ResponseBuilder {
        private final HttpStatus status;
        private final List<String> messages;

        public ResponseBuilder(HttpStatus status, List<String> messages) {
            this.status = status;
            this.messages = messages;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public List<String> getMessages() {
            return messages;
        }
    }

    private List<String> parseErrorMessage(String errorMessage) {
        List<String> messages = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> errors = objectMapper.readValue(errorMessage, HashMap.class);
            errors.forEach((element, message) -> messages.add(message));
        } catch (IOException e) {
            messages.add(errorMessage);
            logger.error("Failed to parse error message: ", e);
        }
        return messages;
    }
}
