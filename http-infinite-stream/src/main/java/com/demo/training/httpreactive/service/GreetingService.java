package com.demo.training.httpreactive.service;

import com.demo.training.httpreactive.entity.GreetingRequest;
import com.demo.training.httpreactive.entity.GreetingResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@Service
public class GreetingService {

    private GreetingResponse greet(String name) {
        return new GreetingResponse("Hello " + name + " @ " + Instant.now());
    }

    public Flux<GreetingResponse> greetMany(GreetingRequest request) {
        return Flux
                .fromStream(Stream.generate(() -> greet(request.getName())))
                .delayElements(Duration.ofSeconds(1))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<GreetingResponse> greetOnce(GreetingRequest request) {
        return Mono.just(greet(request.getName()));
    }
}
