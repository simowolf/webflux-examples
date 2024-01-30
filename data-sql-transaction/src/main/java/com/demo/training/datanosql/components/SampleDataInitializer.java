package com.demo.training.datanosql.components;

import com.demo.training.datanosql.entity.Person;
import com.demo.training.datanosql.repository.PersonRepository;
import com.demo.training.datanosql.service.PersonService;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
@Log4j2
public class SampleDataInitializer {

    private final PersonRepository personRepository;
    private final PersonService personService;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {

        Flux<Person> persons =
                this.personService.saveAll("Mario,Rossi", "Giuseppe,Verdi", "Giovanni,Gialli", "Lorenzo,Bianchi", "Pietro,Neri");

        log.info("This is on main thread!!");

        this.personRepository
                .deleteAll()
                .thenMany(persons)
                .thenMany(this.personRepository.findAll())
                .subscribe(log::info);
    }
}
