package com.inpeco.training.securityhttpclient.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.ExchangeFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Bean
    WebClient webClient (WebClient.Builder builder) {
        return builder
                .filter(ExchangeFilterFunctions.basicAuthentication("user", "password"))
                .build();
    }
}
