package com.bernmpdev.javereurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class JaverEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JaverEurekaServerApplication.class, args);
    }

}
