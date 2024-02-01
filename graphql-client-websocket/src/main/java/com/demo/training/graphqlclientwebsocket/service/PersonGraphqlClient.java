package com.demo.training.graphqlclientwebsocket.service;

import com.demo.training.graphqlclientwebsocket.entity.Person;
import com.demo.training.graphqlclientwebsocket.entity.PersonEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.graphql.client.WebSocketGraphQlClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.*;

@Component
@Log4j2
@RequiredArgsConstructor
public class PersonGraphqlClient {

    private final WebSocketGraphQlClient graphQlClient;

    public Flux<Person> getPersons() {
        return graphQlClient
                .document("query { persons { id name surname }}")
                .retrieve("persons")
                .toEntityList(Person.class)
                .flatMapMany(Flux::fromIterable);
    }

    public Flux <PersonEvent> getPersonEvents(Integer id) {
        return graphQlClient.
                document("subscription { personEvents(id: \"" + id.toString() + "\") { event , person { id name surname } } }")
                .retrieveSubscription("personEvents")
                .toEntity(PersonEvent.class);
    }
}
