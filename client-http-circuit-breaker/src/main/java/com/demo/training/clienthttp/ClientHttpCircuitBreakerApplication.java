package com.demo.training.clienthttp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ClientHttpCircuitBreakerApplication {

    @Bean
    WebClient webClient (WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8080").build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientHttpCircuitBreakerApplication.class, args);
    }

}
