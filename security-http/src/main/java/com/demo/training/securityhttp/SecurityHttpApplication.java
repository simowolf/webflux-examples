package com.demo.training.securityhttp;

import com.demo.training.securityhttp.entity.GreetingRequest;
import com.demo.training.securityhttp.entity.GreetingResponse;
import com.demo.training.securityhttp.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class SecurityHttpApplication {

    @Bean
    RouterFunction<ServerResponse> routes(GreetingService gs) {
        return route()
                .GET("/greetings", request -> {
                    Flux<GreetingResponse> greetingResponseFlux = request
                            .principal()
                            .map(Principal::getName)
                            .map(GreetingRequest::new)
                            .flatMapMany(gs::greetMany);
                    return ServerResponse
                            .ok()
                            .contentType(MediaType.TEXT_EVENT_STREAM)
                            .body(greetingResponseFlux, GreetingResponse.class);
                })

                .GET("/greeting", request -> {
                    Mono<GreetingResponse> greetingResponseMono = request
                            .principal()
                            .map(Principal::getName)
                            .map(GreetingRequest::new)
                            .flatMap(gs::greetOnce);
                    return ServerResponse
                            .ok()
                            .contentType(MediaType.TEXT_EVENT_STREAM)
                            .body(greetingResponseMono, GreetingResponse.class);
                })
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityHttpApplication.class, args);
    }

}
