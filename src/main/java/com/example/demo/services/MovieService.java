package com.example.demo.services;

import com.example.demo.DTOs.CreateMovieRequest;
import com.example.demo.models.Movie;
import com.example.demo.repositories.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final MovieRepository movieRepo;

    public MovieService(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    public Movie createMovie(CreateMovieRequest newMovie) {
//        Add validation HERE
        Movie movie = new Movie();
        movie.setTitle(newMovie.getTitle());
        movie.setReleaseYear(newMovie.getReleaseYear());
        movie.setDirector(newMovie.getDirector());
        return movieRepo.save(movie);
    }

    public Movie findMovieById(Long id) {
        return movieRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                "Movie with ID: %d, was not found", id
        )));
    }



}
