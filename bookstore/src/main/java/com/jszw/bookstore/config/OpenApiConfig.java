package com.jszw.bookstore.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class OpenApiConfig {

    //@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bookstore API")
                        .version("1.0.0")
                        .description("Documentación de la API REST para el sistema Bookstore")
                        .contact(new Contact()
                                .name("Julián Szwarcman")
                                .email("support@bookstore.com")
                                .url("https://bookstore.com"))
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio GitHub")
                        .url("https://github.com/tu-usuario/bookstore"));
    }
}
