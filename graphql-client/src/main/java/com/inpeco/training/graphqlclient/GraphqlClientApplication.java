package com.inpeco.training.graphqlclient;

import com.inpeco.training.graphqlclient.entity.Person;
import com.inpeco.training.graphqlclient.service.PersonGraphqlClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.client.WebSocketGraphQlClient;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.socket.client.ReactorNetty2WebSocketClient;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class GraphqlClientApplication {

    @Bean
    WebClient graphQlClient (WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8080").build();
    }


//    @Bean
//    RouterFunction<ServerResponse> routes(PersonGraphqlClient pgc) {
//        return route()
//                .GET("/persons", request ->
//                        ok()
//                                .contentType(MediaType.TEXT_EVENT_STREAM)
//                                .body(pgc.getPersons(), Person.class))
//                .build();
//    }

    public static void main(String[] args) {
        SpringApplication.run(GraphqlClientApplication.class, args);
    }

}
