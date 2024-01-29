package com.demo.training.graphqlintro.components;

import com.demo.training.graphqlintro.entity.Order;
import com.demo.training.graphqlintro.entity.Person;
import com.demo.training.graphqlintro.entity.PersonEvent;
import com.demo.training.graphqlintro.entity.PersonEventType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


@Component
public class PersonClient {

    public PersonClient() {
        Flux.just("Simone,Lupo", "Giulio,Giorgio", "Bill,Mono", "Claudio,Mattioni", "Luca,Romitelli")
                .flatMap(name -> addPerson(name.split(",")[0], name.split(",")[1]))
                .subscribe(person -> {
                    var list = this.db.get(person);
                    for (var orderId = 1; orderId <= (Math.random() * 100); orderId++) {
                        list.add(new Order(orderId, person.id()));
                    }
                });
    }

    private final Map<Person, Collection<Order>> db = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public Flux<Person> getPersons() {
        return Flux.fromIterable(this.db.keySet());
    }

    public Mono<Person> getPersonById(Integer personId) {
        return getPersons().filter(person -> person.id().equals(personId)).singleOrEmpty();
    }

    public Flux<Person> getPersonsByName(String name) {
        return getPersons().filter(person -> person.name().equalsIgnoreCase(name));
    }

    public Flux<Person> getPersonsBySurname(String surname) {
        return getPersons().filter(person -> person.surname().equalsIgnoreCase(surname));
    }

    public Flux<Person> getPersonsByNameAndSurname(String name, String surname) {
        return getPersons().filter(person ->
                person.name().equalsIgnoreCase(name) && person.surname().equalsIgnoreCase(surname));
    }

    public Mono<Person> addPerson(String name, String surname) {
        var key = new Person(id(), name, surname);
        this.db.put(key, new CopyOnWriteArrayList<>());
        return Mono.just(key);
    }

    public Flux<Order> getOrdersForPersonId(Integer personId) {
        return getPersonById(personId)
                .map(this.db::get)
                .flatMapMany(Flux::fromIterable);
    }

    public Flux<PersonEvent> getPersonEvents(Integer personId) {
        return getPersonById(personId)
                .flatMapMany(person -> {
                    return Flux.fromStream(Stream.generate(() -> {
                        var event = Math.random() > .5 ? PersonEventType.UPDATED : PersonEventType.CREATED;
                        return new PersonEvent(person, event);
                    }));
                })
                .take(10)
                .delayElements(Duration.ofSeconds(1));
    }

    private int id() {
        return this.id.incrementAndGet();
    }
}
