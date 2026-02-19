package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//RESTful API
// GET /api/customers -> Returns all customers in DB
// GET /api/customers/:id -> Return Customer with ID (id)

// POST /api/customers -> Create a new customer

@RestController
public class PingPong {

    @GetMapping(value = "/welcome", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String welcomeAsHTML() {
        return """
                <html>
                <header><title>Welcome</title></header>
                <body>
                Hello world
                </body>
                </html>""";
    }

    @GetMapping("/")
    public String homePage() {
        return "Welcome";
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
