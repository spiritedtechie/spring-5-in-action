package com.blah.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
            path = "/users",
            method = RequestMethod.POST)
    public CompletableFuture<User> createUser(@RequestBody User user) {
        return this.userService.newUser(user.getFirstname(), user.getLastname());
    }

    @RequestMapping(
            path = "/users/{id}",
            method = RequestMethod.GET)
    public CompletableFuture<Optional<User>> getUser(@PathVariable("id") Long id) {
        return this.userService.findUser(id);
    }

    @RequestMapping(
            path = "/users",
            method = RequestMethod.GET)
    public CompletableFuture<List<User>> getAllUsers() {
        return this.userService.findAllUsers();
    }

    @RequestMapping(
            path = "/users/name",
            method = RequestMethod.GET)
    public CompletableFuture<List<User>> getUsersByFirstname(@RequestParam(name = "firstname") String firstname) {
        return this.userService.findUserByFirstname(firstname);
    }
}