package com.diagorn.sparkathon.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@ConfigurationPropertiesScan
public class AuthenticationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }
}
