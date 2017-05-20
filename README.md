## Overview
Exploring the new reactive web capabilities of Spring 5.

The following are the applications that can be booted up:

1. Futures - uses CompletableFuture for return type in RestControllers
2. Reactor - uses Mono and Flux Reactor types, and Spring reactive web's RouterFunction and HandlerFunction rather than Controllers

## Running

There is a Spring boot Application class in each of the packages that can be used to start each application.

## Futures App Endpoints
```
curl -d '{"firstname":"Bob", "lastname":"Jones"}' -H "Content-Type:application/json" http://localhost:8080/futures/users
curl http://localhost:8080/futures/users
curl http://localhost:8080/futures/users/1
curl http://localhost:8080/futures/users/name?firstname=Bob
```
## Reactor App Endpoints
```
curl -d '{"firstname":"Bob", "lastname":"Jones"}' -H "Content-Type:application/json" http://localhost:8080/reactor/users
curl http://localhost:8080/reactor/users
curl http://localhost:8080/reactor/users/1
curl http://localhost:8080/reactor/users/name?firstname=Bob
```
