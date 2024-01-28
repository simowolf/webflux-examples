package com.inpeco.training.securityhttp.service;

import com.inpeco.training.securityhttp.entity.GreetingRequest;
import com.inpeco.training.securityhttp.entity.GreetingResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@Service
public class GreetingService {

    private GreetingResponse greet(GreetingRequest request) {
        return new GreetingResponse("Hello " + request.getName() + " @ " + Instant.now() + "!");
    }

    public Flux<GreetingResponse> greetMany(GreetingRequest request) {
        return Flux
                .fromStream(Stream.generate(() -> greet(request)))
                .delayElements(Duration.ofSeconds(1))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<GreetingResponse> greetOnce(GreetingRequest request) {
        return Mono.just(greet(request));
    }
}
