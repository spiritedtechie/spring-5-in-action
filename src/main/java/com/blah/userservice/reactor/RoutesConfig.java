package com.blah.userservice.reactor;

import com.blah.userservice.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutesConfig {

    private UserService userService;

    @Autowired
    public RoutesConfig(UserService userService) {
        this.userService = userService;
    }

    HandlerFunction newUser = request -> {
        Mono<User> body = request.bodyToMono(User.class);
        return body
                .map(user -> userService.newUser(user));
    };

    HandlerFunction getAllUsers = request -> {
        Flux<User> users = userService.findAllUsers();
        return ServerResponse.ok().body(fromPublisher(users, User.class));
    };

    @Bean
    public RouterFunction<?> getRouter() {
        RouterFunction routes =
                route(POST("/users"), newUser).and(
                        route(GET("/users"), getAllUsers));

        return nest(path("/reactor"), routes);
    }


}