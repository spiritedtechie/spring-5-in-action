package com.blah.userservice.futures;

import com.blah.userservice.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CompletableFuture<User> newUser(String firstname, String lastname) {
        return userRepository.save(new User(firstname, lastname));
    }

    public CompletableFuture<Optional<User>> findUser(Long id) {
        return userRepository.findById(id);
    }

    public CompletableFuture<List<User>> findAllUsers() {
        return userRepository.findAll();
    }

    public CompletableFuture<List<User>> findUserByFirstname(String firstname) {
        return userRepository.findByFirstname(firstname);
    }

}
