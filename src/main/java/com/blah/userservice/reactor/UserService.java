package com.blah.userservice.reactor;

import com.blah.userservice.data.User;
import com.blah.userservice.futures.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static reactor.core.publisher.Mono.from;
import static reactor.core.publisher.Mono.fromFuture;

@Component
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> newUser(User userToCreate) {

//        userRepository.save(userToCreate).

        return Mono.just(userToCreate)
                .flatMap(u -> fromFuture(userRepository.save(u)));
    }

    public Mono<Optional<User>> findUser(Long id) {
        return Mono.just(id)
                .flatMap(i -> fromFuture(userRepository.findById(i)));
    }

    public Flux<User> findAllUsers() {
        return null;
    }

//    public Flux<User> findUserByFirstname(String firstname) {
//        return userRepository.findByFirstname(firstname);
//    }

}
