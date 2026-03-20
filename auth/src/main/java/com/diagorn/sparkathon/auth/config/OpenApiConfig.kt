package com.diagorn.sparkathon.auth.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Swagger configuration
 *
 * @author diagorn
 */
@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
            .info(
                Info()
                    .title(TITLE)
                    .description(DESCRIPTION)
                    .version(VERSION)
                    .contact(
                        Contact()
                            .name(CONTACT_NAME)
                            .email(CONTACT_EMAIL)
                            .url(CONTACT_URL)
                    )
            )

    companion object {
        const val TITLE = "auth-service API"
        const val DESCRIPTION = "User and authentication service API"
        const val VERSION = "1.0.0"

        const val CONTACT_NAME = "Mikhail Gasin"
        const val CONTACT_EMAIL = "diagorn1999@yandex.ru"
        const val CONTACT_URL = "https://github.com/Diagorn"
    }
}