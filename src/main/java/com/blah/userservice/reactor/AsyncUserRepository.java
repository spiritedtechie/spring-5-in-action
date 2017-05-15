package com.blah.userservice.reactor;

import com.blah.userservice.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class AsyncUserRepository implements Repository<User, Long> {

    private UserRepository userRepository;

    @Autowired
    public AsyncUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Mono<User> save(User user) {
        return Mono.just(user).publishOn(Schedulers.parallel())
                .doOnNext(userRepository::save);
    }

    Flux<User> findAll() {
        return Flux.defer(() -> Flux.fromStream(userRepository.findAll())).subscribeOn(Schedulers.elastic());
    }

    Mono<User> findById(Long id) {
        return Mono.defer(() -> Mono.justOrEmpty(userRepository.findById(id))).subscribeOn(Schedulers.elastic());
    }

    Flux<User> findByFirstname(String name) {
        return Flux.defer(() -> Flux.fromStream(userRepository.findByFirstname(name))).subscribeOn(Schedulers.elastic());
    }

}
