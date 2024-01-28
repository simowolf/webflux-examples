package com.inpeco.training.graphqlintro.components;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Component;

@Component
public class PersonRuntimeWiringConfigurer implements RuntimeWiringConfigurer {
    private final PersonClient personClient;

    public PersonRuntimeWiringConfigurer(PersonClient personClient) {
        this.personClient = personClient;
    }


    @Override
    public void configure(RuntimeWiring.Builder builder) {
        builder.type("Query", b -> b.dataFetcher("persons", env -> personClient.getPersons()));
    }
}
