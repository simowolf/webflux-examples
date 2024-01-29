package com.demo.training.clienthttp.beans;

import com.demo.training.clienthttp.entity.GreetingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.boot.context.event.ApplicationReadyEvent;
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
@RequiredArgsConstructor
public class Client {

    private final WebClient webClient;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {

        var name = "Spring Fans";

        webClient
                .get()
                .uri("/greeting/{name}", name)
                .retrieve()
                .bodyToMono(GreetingResponse.class)
                .map(GreetingResponse::getMessage)
                .retryWhen(Retry.backoff(10, Duration.ofSeconds(2)))
                .onErrorMap(throwable -> new Exception("Original exception was " + throwable.toString()))
                .onErrorResume(Exception.class, exception -> Mono.just(exception.toString()))
                .subscribe(greetingMessage -> log.info("Mono: " + greetingMessage));

        webClient
                .get()
                .uri("/greetings/{name}", name)
                .retrieve()
                .bodyToFlux(GreetingResponse.class)
                .map(GreetingResponse::getMessage)
                .retryWhen(Retry.backoff(10, Duration.ofSeconds(2)))
                .onErrorMap(throwable -> new Exception("Original exception was " + throwable.toString()))
                .onErrorResume(Exception.class, exception -> Flux.just(exception.toString()))
                .subscribe(greetingMessage -> log.info("Flux: " + greetingMessage));
    }
}
