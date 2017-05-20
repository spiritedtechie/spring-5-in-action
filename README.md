## Overview
Exploring the new reactive web capabilities of Spring 5.

The following are the applications that can be booted up:

1. Futures - uses CompletableFuture for return type in RestControllers
2. Reactor - uses RouterFunction and HandlerFunction rather than Controllers

## Running

There is a Application class in each of the packages to start the different Spring boot applications.

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