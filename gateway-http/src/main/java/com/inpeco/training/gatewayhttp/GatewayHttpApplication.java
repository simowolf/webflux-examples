package com.inpeco.training.gatewayhttp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.PrincipalNameKeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@SpringBootApplication
@EnableWebFluxSecurity
public class GatewayHttpApplication {

    @Bean
    RedisRateLimiter redisRateLimiter() {
        RedisRateLimiter limiter = new RedisRateLimiter(5, 7);
        return limiter;

    }

    @Bean
    SecurityWebFilterChain authorization(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(withDefaults())
                .authorizeExchange(exc -> exc.pathMatchers("/proxy").authenticated())
                .build();
    }

    @Bean
    MapReactiveUserDetailsService authentication() {
        return new MapReactiveUserDetailsService(
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build()
        );
    }


    @Bean
    RouteLocator gateway(RouteLocatorBuilder locatorBuilder) {
        return locatorBuilder
                .routes()
                .route(routeSpec -> routeSpec.host("*.spring.io").and().path("/proxy")
                        .filters(filterSpec -> filterSpec
                                .setPath("/reservation")
                                .addResponseHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                                .requestRateLimiter(rl -> rl
                                        .setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(new PrincipalNameKeyResolver())
                                )
                        )
                        .uri("http://localhost:8080/"))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayHttpApplication.class, args);
    }

}
