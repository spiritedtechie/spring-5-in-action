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
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutesConfig {

    private UserRepository userRepository;

    @Autowired
    public RoutesConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Functions
    static Function<Function<Mono<User>, Mono<User>>, HandlerFunction> handleSave =
            userPersister -> request -> {
                Mono<User> toCreate = request.bodyToMono(User.class);
                return ServerResponse.ok().
                        body(fromPublisher(userPersister.apply(toCreate), User.class));
            };

    static Function<Supplier<Flux<User>>, HandlerFunction> handleFetchAll =
            usersSupplier -> request -> ServerResponse.ok().body(fromPublisher(usersSupplier.get(), User.class));

    static Function<Function<Long, Mono<User>>, HandlerFunction> handleFetchOne =
            userSupplier -> request -> {
                Long userId = Long.valueOf(request.pathVariable("userid"));
                return ServerResponse.ok().body(fromPublisher(userSupplier.apply(userId), User.class));
            };

    static Function<UserRepository, HandlerFunction> getAllUsersUsingRepository = userRepository ->
            handleFetchAll.apply(() ->
                    Flux.defer(() -> Flux.fromStream(userRepository.findAll()))
                            .subscribeOn(Schedulers.elastic()));

    static Function<UserRepository, HandlerFunction> getAUserUsingRepository = userRepository ->
            handleFetchOne.apply(userId ->
                    Mono.defer(() -> Mono.justOrEmpty(userRepository.findById(userId)))
                            .subscribeOn(Schedulers.elastic()));

    static Function<UserRepository, HandlerFunction> saveUserUsingRepository = userRepository ->
            handleSave.apply(user ->
                    user.publishOn(Schedulers.parallel())
                            .doOnNext(u -> userRepository.save(u)));

    @Bean
    public RouterFunction<?> getRouter() {
        RouterFunction routes =
                route(POST("/users"), saveUserUsingRepository.apply(userRepository))
                        .and(route(GET("/users"), getAllUsersUsingRepository.apply(userRepository)))
                        .and(route(GET("/users/{userid}"), getAUserUsingRepository.apply(userRepository)));

        return nest(path("/reactor"), routes);
    }

}