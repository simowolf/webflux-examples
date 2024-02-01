package com.demo.training.graphqlclientwebsocket;

import com.demo.training.graphqlclientwebsocket.entity.Person;
import com.demo.training.graphqlclientwebsocket.entity.PersonEvent;
import com.demo.training.graphqlclientwebsocket.service.PersonGraphqlClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.client.WebSocketGraphQlClient;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class GraphqlClientWebsocketApplication {

    @Bean
    WebSocketGraphQlClient graphQlClient () {
        ReactorNettyWebSocketClient webSocketClient = new ReactorNettyWebSocketClient();
        return WebSocketGraphQlClient.builder("ws://localhost:8080/graphql-ws", webSocketClient).build();
    }

    @Bean
    RouterFunction<ServerResponse> routes(PersonGraphqlClient graphqlClient) {
        return route()
                .GET("/persons", request ->
                        ok().contentType(MediaType.TEXT_EVENT_STREAM)
                                .body(graphqlClient.getPersons(), Person.class))
                .GET("/personevents/{id}", request ->
                        ok().contentType(MediaType.TEXT_EVENT_STREAM)
                                .body(graphqlClient
                                        .getPersonEvents(Integer.valueOf(request.pathVariable("id"))), PersonEvent.class))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(GraphqlClientWebsocketApplication.class, args);
    }

}
