package com.jszw.bookstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger UI con springdoc-openapi.
 * UI: http://localhost:8080/bookstore/api/v1/swagger-ui.html
 * Docs JSON: http://localhost:8080/bookstore/api/v1/v3/api-docs
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bookstore API")
                        .version("1.0")
                        .description("Documentación de la API REST de la librería")
                        .contact(new Contact()
                                .name("Julián Szwarcman")
                                .url("https://github.com/jszw")
                                .email("tu_email@example.com")
                        )
                );
    }
}
