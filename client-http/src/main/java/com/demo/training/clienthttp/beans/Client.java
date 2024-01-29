package com.demo.training.clienthttp.beans;

import com.demo.training.clienthttp.entity.GreetingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
                .subscribe(gr -> log.info("Mono: " + gr.getMessage()));
        webClient
                .get()
                .uri("/greetings/{name}", name)
                .retrieve()
                .bodyToFlux(GreetingResponse.class)
                .subscribe(gr -> log.info("Flux: " + gr.getMessage()));
    }
}
