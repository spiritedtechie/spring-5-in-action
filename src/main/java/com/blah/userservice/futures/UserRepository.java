package com.blah.userservice.futures;

import com.blah.userservice.data.User;
import org.springframework.data.repository.Repository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface UserRepository extends Repository<User, Long> {

    @Async
    CompletableFuture<User> save(User user);

    @Async
    CompletableFuture<Optional<User>> findById(Long id);

    @Async
    CompletableFuture<Stream<User>> findByFirstname(String firstname);

    @Async
    CompletableFuture<Stream<User>> findAll();
}
