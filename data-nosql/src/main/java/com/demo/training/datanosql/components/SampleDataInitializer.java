package com.demo.training.datanosql.components;

import com.demo.training.datanosql.entity.Person;
import com.demo.training.datanosql.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
@Log4j2
public class SampleDataInitializer {

    private final PersonRepository personRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {

        Flux<Person> persons = Flux
                .just("Mario,Rossi", "Giuseppe,Verdi", "Giovanni,Gialli", "Lorenzo,Bianchi", "Pietro,Neri")
                .map(name -> new Person(null, name.split(",")[0], name.split(",")[1]))
                .flatMap(this.personRepository::save);

        log.info("This is on main thread!!");

        this.personRepository
                .deleteAll()
                .thenMany(persons)
                .thenMany(this.personRepository.findAll())
                .subscribe(log::info);
    }
}
