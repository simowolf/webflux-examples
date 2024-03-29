package com.demo.training.httpmvc.service;

import com.demo.training.httpmvc.entity.GreetingRequest;
import com.demo.training.httpmvc.entity.GreetingResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class GreetingService {

    private GreetingResponse greet(String name) {
        return new GreetingResponse("Hello " + name + " @ " + Instant.now());
    }

    public Mono<GreetingResponse> greet(GreetingRequest request) {
        return Mono.just(greet(request.getName()));
    }
}
