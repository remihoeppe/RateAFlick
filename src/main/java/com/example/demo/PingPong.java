package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
