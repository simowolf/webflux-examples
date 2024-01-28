package com.inpeco.training.datanosql.repository;

import com.inpeco.training.datanosql.entity.Person;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PersonRepository extends ReactiveCrudRepository<Person, String> {

    @Tailable
    Flux<Person> findByName(String name);
    Flux<Person> findBySurname(String surname);
    Flux<Person> findByNameAndSurname(String name, String surname);

}
