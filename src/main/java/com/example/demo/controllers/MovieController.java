package com.example.demo.controllers;

import com.example.demo.DTOs.CreateMovieRequest;
import com.example.demo.DTOs.MovieResponse;
import com.example.demo.services.MovieService;
import jakarta.validation.Valid;
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
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieById(id));
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody CreateMovieRequest newMovie) {
        MovieResponse createdMovie = movieService.createMovie(newMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

}
