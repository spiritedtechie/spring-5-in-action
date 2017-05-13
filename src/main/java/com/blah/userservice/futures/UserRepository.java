package com.blah.userservice.futures;

import com.blah.userservice.User;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface UserRepository extends Repository<User, Long> {

    @Async
    CompletableFuture<User> save(User user);

    @Async
    CompletableFuture<Optional<User>> findById(Long id);

    @Async
    CompletableFuture<List<User>> findByFirstname(String firstname);

    @Async
    CompletableFuture<List<User>> findAll();
}
