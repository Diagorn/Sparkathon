package com.diagorn.sparkathon.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("auth-service API")
                        .description("User and authentication service API")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Mikhail Gasin")
                                .email("diagorn1999@yandex.ru")
                                .url("https://github.com/Diagorn")));
    }
}
