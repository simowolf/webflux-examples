package com.demo.training.demo.repository;

import com.demo.training.demo.entity.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PersonRepository extends ReactiveCrudRepository<Person, String> {

    Flux<Person> findByName(String name);
    Flux<Person> findBySurname(String surname);
    Flux<Person> findByNameAndSurname(String name, String surname);
}
