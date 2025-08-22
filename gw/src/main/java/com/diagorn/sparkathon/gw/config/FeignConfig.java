package com.diagorn.sparkathon.gw.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.util.List;

/**
 * Feign configuration
 *
 * @author diagorn
 */
@Configuration
@EnableFeignClients(basePackages = "com.diagorn.sparkathon.gw.client")
public class FeignConfig {

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        return new HttpMessageConverters(true, List.of(new StringHttpMessageConverter()));
    }
}
