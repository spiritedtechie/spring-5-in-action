package com.blah.userservice.reactor;

import com.blah.userservice.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

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

    HandlerFunction createNewUser = request -> ServerResponse.ok().
            body(fromPublisher(userService.newUser(request.bodyToMono(User.class)), User.class));

    HandlerFunction getAllUsers = request ->
            ServerResponse.ok().body(fromPublisher(userService.findAllUsers(), User.class));

    @Bean
    public RouterFunction<?> getRouter() {
        RouterFunction routes =
                route(POST("/users"), createNewUser).and(
                        route(GET("/users"), getAllUsers));

        return nest(path("/reactor"), routes);
    }


}