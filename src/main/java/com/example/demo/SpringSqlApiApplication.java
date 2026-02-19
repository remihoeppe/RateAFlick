package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSqlApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSqlApiApplication.class, args);
	}

//	We want to build an API for Users to give Movie Ratings
//	One User should be able to rate multiple Movies (edge case: only one
//	reating per movie per user)
	// One Movie can have many Ratings
//	A Rating is specific to a User and to a Movie




//	CLIENT (WEB APP - MOBILE APP) /// DTO - (Models)  /// -> Controllers (HTTP)
//	-> Services
//	(Logic) ->  Repositories (Data Access) -> DB

}
