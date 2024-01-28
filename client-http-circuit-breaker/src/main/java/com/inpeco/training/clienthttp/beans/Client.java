package com.inpeco.training.clienthttp.beans;

import com.inpeco.training.clienthttp.entity.GreetingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@Log4j2
public class Client {

    private final WebClient webClient;
    private final ReactiveCircuitBreaker circuitBreaker;

    public Client(WebClient webClient, ReactiveCircuitBreakerFactory circuitBreakerFactory) {
        this.webClient = webClient;
        this.circuitBreaker = circuitBreakerFactory.create("greeting");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {

        var name = "Spring Fans";

        Mono<String> http = this.webClient
                .get()
                .uri("/greeting/{name}", name)
                .retrieve()
                .bodyToMono(GreetingResponse.class)
                .map(GreetingResponse::getMessage);

        this.circuitBreaker
                .run(http, throwable -> Mono.just("Error!!!"))
                .subscribe(greetingMessage -> log.info("Mono: " + greetingMessage));
    }
}
