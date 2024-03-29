package com.demo.training.httpmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class HttpMvcApplication {


    public static void main(String[] args) {
        SpringApplication.run(HttpMvcApplication.class, args);
    }

}
