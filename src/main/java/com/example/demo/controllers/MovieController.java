package com.example.demo.controllers;

import com.example.demo.DTOs.CreateMovieRequest;
import com.example.demo.models.Movie;
import com.example.demo.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieById(id));
    }

    // @GetMapping

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody CreateMovieRequest newMovie) {
        Movie createdMovie = movieService.createMovie(newMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

}
