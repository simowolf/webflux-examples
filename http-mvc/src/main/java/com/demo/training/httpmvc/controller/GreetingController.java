package com.demo.training.httpmvc.controller;

import com.demo.training.httpmvc.entity.GreetingRequest;
import com.demo.training.httpmvc.entity.GreetingResponse;
import com.demo.training.httpmvc.service.GreetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class GreetingController {
    private final GreetingService greetingService;

    @GetMapping("/greetings/{name}")
    Mono<GreetingResponse> greet(@PathVariable String name) {
        return this.greetingService.greet(new GreetingRequest(name));
    }
}
