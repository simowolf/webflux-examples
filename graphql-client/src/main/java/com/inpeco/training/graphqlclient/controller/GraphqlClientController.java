package com.inpeco.training.graphqlclient.controller;

import com.inpeco.training.graphqlclient.entity.Person;
import com.inpeco.training.graphqlclient.service.PersonGraphqlClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@AllArgsConstructor
public class GraphqlClientController {

    private final PersonGraphqlClient pgs;
    @GetMapping("/persons")
    public List<Person> getPersons() {

        return pgs.getPersons();
    }
}
