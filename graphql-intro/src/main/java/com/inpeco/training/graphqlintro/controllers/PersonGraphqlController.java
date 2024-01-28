package com.inpeco.training.graphqlintro.controllers;

import com.inpeco.training.graphqlintro.components.PersonClient;
import com.inpeco.training.graphqlintro.entity.Order;
import com.inpeco.training.graphqlintro.entity.Person;
import com.inpeco.training.graphqlintro.entity.PersonEvent;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class PersonGraphqlController {

    private final PersonClient personClient;

    public PersonGraphqlController(PersonClient personClient) {
        this.personClient = personClient;
    }

//    @SchemaMapping(typeName = "Query", field = "persons")
    @QueryMapping
    Flux<Person> persons() {
        return this.personClient.getPersons();
    }

    @QueryMapping
    Flux<Person> personsByName(@Argument String name) {
        return this.personClient.getPersonsByName(name);
    }

    @QueryMapping
    Flux<Person> personsBySurname(@Argument String surname) {
        return this.personClient.getPersonsBySurname(surname);
    }

    @QueryMapping
    Flux<Person> personsByNameAndSurname(@Argument String name, @Argument String surname) {
        return this.personClient.getPersonsByNameAndSurname(name, surname);
    }

    @SchemaMapping(typeName = "Person")
    Flux<Order> orders(Person person) {
        return this.personClient.getOrdersForPersonId(person.id());
    }

    @MutationMapping
    Mono<Person> addPerson(@Argument String name, @Argument String surname) {
        return this.personClient.addPerson(name, surname);
    }

    @SubscriptionMapping
    Flux<PersonEvent> personEvents(@Argument Integer id) {
        return this.personClient.getPersonEvents(id);
    }
}
