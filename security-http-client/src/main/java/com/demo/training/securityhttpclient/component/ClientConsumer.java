package com.demo.training.securityhttpclient.component;

import com.demo.training.securityhttpclient.entity.GreetingResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Log4j2
public class ClientConsumer {

    private final WebClient client;

    @EventListener(ApplicationReadyEvent.class)
    private void ready() {
        this.client.get().uri("http://localhost:8080/greetings").retrieve()
                .bodyToFlux(GreetingResponse.class).subscribe(log::info);
    }
}
