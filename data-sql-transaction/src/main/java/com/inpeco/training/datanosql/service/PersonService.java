package com.inpeco.training.datanosql.service;

import com.inpeco.training.datanosql.entity.Person;
import com.inpeco.training.datanosql.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
@Transactional
public class PersonService {

    private final TransactionalOperator transactionalOperator;
    private final PersonRepository personRepository;

    @Transactional
    public Flux<Person> saveAll(String... names) {
        Flux<Person> persons = Flux
                .fromArray(names)
                .map(name -> new Person(null, name.split(",")[0], name.split(",")[1]))
                .flatMap(this.personRepository::save)
                .doOnNext(this::assertValid);
//        return this.transactionalOperator.transactional(persons);
        return persons;
    }

    private void assertValid(Person p) {
        Assert.isTrue(p.getName() != null
                        && !p.getName().isEmpty()
                        && Character.isUpperCase(p.getName().charAt(0))
                        && p.getSurname() != null
                        && !p.getSurname().isEmpty()
                        && Character.isUpperCase(p.getSurname().charAt(0)),
                "Name and Surname must begin in capital letters!");
    }
}
