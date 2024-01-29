package com.demo.training.datanosql.repository;

import com.demo.training.datanosql.entity.Person;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PersonRepository extends ReactiveCrudRepository<Person, Integer> {

    @Query("select * from person where name = $1")
    Flux<Person> findByName(String name);
    @Query("select * from person where surname = $1")
    Flux<Person> findBySurname(String surname);
    @Query("select * from person where name = $1 and surname = $2")
    Flux<Person> findByNameAndSurname(String name, String surname);

}
