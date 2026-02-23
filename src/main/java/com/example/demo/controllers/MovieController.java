package com.example.demo.controllers;

import com.example.demo.dto.common.PageResponse;
import com.example.demo.dto.movie.CreateMovieRequest;
import com.example.demo.dto.movie.MovieResponse;
import com.example.demo.services.MovieService;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movies")
@Validated
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<MovieResponse>> getAllMovies(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(movieService.findAllMovies(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(movieService.find(id));
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody CreateMovieRequest newMovie) {
        MovieResponse createdMovie = movieService.createMovie(newMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

}
