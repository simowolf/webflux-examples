package com.demo.training.demo.components;

import com.demo.training.demo.entity.Person;
import com.demo.training.demo.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class SampleDataInitializer {

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    private final PersonRepository personRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {



        reactiveMongoTemplate.createCollection(
                Person.class,
                CollectionOptions.empty().capped().size(10_000_000)
                        .maxDocuments(20)
        );

        Flux<Person> persons = Flux
                .just("Simone,Lupo", "Giulio,Giorgio", "Bill,Mono", "Claudio,Mattioni", "Luca,Romitelli")
                .map(name -> new Person(null, name.split(",")[0], name.split(",")[1]))
                .flatMap(this.personRepository::save);

        personRepository
                .deleteAll()
                        .thenMany(persons)
                                .
    }
}
