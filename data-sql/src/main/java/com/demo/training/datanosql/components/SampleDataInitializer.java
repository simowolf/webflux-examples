package com.demo.training.datanosql.components;

import com.demo.training.datanosql.entity.Person;
import com.demo.training.datanosql.repository.PersonRepository;
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

//    private final DatabaseClient databaseClient;

    private final PersonRepository personRepository;

//    public static final BiFunction<Row, RowMetadata, Person> MAPPING_FUNCTION = (row, rowMetaData) -> Person.builder()
//            .id(row.get("id", Integer.class))
//            .name(row.get("name", String.class))
//            .surname(row.get("surname", String.class))
//            .build();
//
//
    @EventListener(ApplicationReadyEvent.class)
    public void ready() {

//        this.databaseClient.sql("SELECT * from person")
//                .map(MAPPING_FUNCTION)
//                .all()
//                .subscribe(log::info);

        Flux<Person> persons = Flux
                .just("Simone,Lupo", "Giulio,Giorgio", "Bill,Mono", "Claudio,Mattioni", "Luca,Romitelli")
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
