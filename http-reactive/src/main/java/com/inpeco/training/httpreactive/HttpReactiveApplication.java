package com.inpeco.training.httpreactive;

import com.inpeco.training.httpreactive.entity.GreetingRequest;
import com.inpeco.training.httpreactive.entity.GreetingResponse;
import com.inpeco.training.httpreactive.service.GreetingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class HttpReactiveApplication {

    @Bean
    RouterFunction<ServerResponse> routes(GreetingService gs) {
        return route()
                .GET("/greetings/{name}", request -> {
                    return ok().body(gs.greet(new GreetingRequest(request.pathVariable("name"))), GreetingResponse.class);
                })
                .build();
    }


    public static void main(String[] args) {
        SpringApplication.run(HttpReactiveApplication.class, args);
    }

}
