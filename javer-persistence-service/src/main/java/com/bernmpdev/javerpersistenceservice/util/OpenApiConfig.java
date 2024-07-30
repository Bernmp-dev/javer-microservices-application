package com.bernmpdev.javerpersistenceservice.util;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
    info = @Info(
    title = "Javer Microservices - Bernmp-dev",
    version = "1.0",
    description = "Open Api documentation",
    summary = "_",
    contact = @Contact(
      name = "Bernmp-dev",
      email = "bernardomp.dev@gmail.com",
      url = "https://github.com/Bernmp-dev"
    )))
@SecurityScheme(
        name = "basicAuth",
        scheme = "basic",
        bearerFormat = "basicAuth",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}

