package com.inpeco.training.demo.repository;

import com.inpeco.training.demo.entity.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PersonRepository extends ReactiveCrudRepository<Person, String> {

    Flux<Person> findByName(String name);
    Flux<Person> findBySurname(String surname);
    Flux<Person> findByNameAndSurname(String name, String surname);
}
