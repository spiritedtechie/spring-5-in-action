package com.blah.userservice.reactor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.ipc.netty.http.server.HttpServer;

@Configuration
public class HttpServerConfig {

    @Bean
    public HttpServer httpServer(RouterFunction<?> routerFunction) {
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(routerFunction);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create("localhost", 8080);
        server.newHandler(adapter);
        return server;

//        Tomcat tomcatServer = new Tomcat();
//        tomcatServer.setHostname("localhost");
//        tomcatServer.setPort(8080);
//        Context rootContext = tomcatServer.addContext("", System.getProperty("java.io.tmpdir"));
//        ServletHttpHandlerAdapter servlet = new ServletHttpHandlerAdapter(httpHandler);
//        Tomcat.addServlet(rootContext, "httpHandlerServlet", servlet);
//        rootContext.addServletMapping("/", "httpHandlerServlet");
//        tomcatServer.start();
    }
}
