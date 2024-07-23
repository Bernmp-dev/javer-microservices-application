package com.bernmpdev.javerproxyservice.authorization;

import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationConfig {

    @Value("${feign.client.username}")
    private String username;

    @Value("${feign.client.password}")
    private String password;

    @Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(username, password);
    }

    @Bean
    public RequestInterceptor originHeaderInterceptor() {
        return (requestTemplate) -> requestTemplate
                .header("X-Origin-Header", "javer-proxy-service");
    }
}
