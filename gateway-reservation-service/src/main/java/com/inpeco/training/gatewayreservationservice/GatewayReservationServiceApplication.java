package com.inpeco.training.gatewayreservationservice;

import com.inpeco.training.gatewayreservationservice.entity.Reservation;
import com.inpeco.training.gatewayreservationservice.repository.ReservationRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.resources;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class GatewayReservationServiceApplication {

    @Bean
    RouterFunction <ServerResponse> routes(ReservationRepository rr) {
        return route()
                .GET("/reservation", request -> ok().body(rr.findAll(), Reservation.class))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayReservationServiceApplication.class, args);
    }

}
