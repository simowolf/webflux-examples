package com.demo.training.clienthttp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ClientHttpApplication {

    @Bean
    WebClient webClient (WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8080").build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientHttpApplication.class, args);
    }

}
