package com.blah.userservice.reactor;

import com.blah.userservice.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserService {

    private AsyncUserRepository userRepository;

    @Autowired
    public UserService(AsyncUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> newUser(Mono<User> userToCreate) {
        return userToCreate.flatMap(u -> userRepository.save(u));
    }

    public Mono<User> findUser(Long id) {
        return userRepository.findById(id);
    }

    public Flux<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Flux<User> findUserByFirstname(String firstname) {
        return userRepository.findByFirstname(firstname);
    }

}
