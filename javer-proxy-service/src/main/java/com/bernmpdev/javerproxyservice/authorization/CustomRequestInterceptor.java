package com.bernmpdev.javerproxyservice.authorization;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRequestInterceptor implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CustomRequestInterceptor.class);

    @Override
    public void apply(RequestTemplate template) {
        // Log headers
        template.headers().forEach((key, values) -> {
            values.forEach(value -> logger.info("Header: {} = {}", key, value));
        });
    }
}
