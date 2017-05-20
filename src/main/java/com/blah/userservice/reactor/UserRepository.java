package com.blah.userservice.reactor;

import com.blah.userservice.data.User;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> save(User user);

    Optional<User> findById(Long id);

    List<User> findByFirstname(String firstname);

    Stream<User> findAll();
}
