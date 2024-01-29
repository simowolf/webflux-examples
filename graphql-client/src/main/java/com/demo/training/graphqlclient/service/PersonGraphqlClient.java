package com.demo.training.graphqlclient.service;

import ch.qos.logback.core.net.server.Client;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.demo.training.graphqlclient.entity.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.graphql.GraphQlResponse;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.client.WebSocketGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.util.*;

@Component
@Log4j2
@RequiredArgsConstructor
public class PersonGraphqlClient {

    private final WebClient graphQlClient;

    public List<Person> getPersons() {
        final List<Person> persons = new ArrayList<>();
        String graphqlQuery = "{\"query\": \"query { persons { id, name, surname, orders { id, personId } } }\"}";
        Mono<String> responseMono = this.graphQlClient
                .post()
                .uri("/graphql")
                .header("Content-Type", "application/json")
                .bodyValue(graphqlQuery)
                .retrieve()
                .bodyToMono(String.class);

        responseMono.subscribe(responseBody -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                log.info(responseBody);
                JsonNode nodes = mapper.readTree(responseBody);
                log.info(nodes);
                log.info(nodes.asText());
                log.info(nodes.get("data").asText());
                String response = nodes.get("data").get("persons").asText();
                List<Person> personList = mapper.readValue(response, new TypeReference<>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });
                persons.addAll(personList);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        return persons;
    }
}
