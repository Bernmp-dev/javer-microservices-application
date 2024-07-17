package com.bernmpdev.javerproxyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JaverProxyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JaverProxyServiceApplication.class, args);
    }

}
