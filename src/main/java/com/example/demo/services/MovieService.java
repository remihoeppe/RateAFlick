package com.example.demo.services;

import com.example.demo.DTOs.CreateMovieRequest;
import com.example.demo.DTOs.MovieResponse;
import com.example.demo.DTOs.RatingResponse;
import com.example.demo.models.Movie;
import com.example.demo.models.Rating;
import com.example.demo.repositories.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepo;

    public MovieService(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    public MovieResponse createMovie(CreateMovieRequest newMovie) {
//        Add validation HERE
        Movie movie = new Movie();
        movie.setTitle(newMovie.getTitle());
        movie.setReleaseYear(newMovie.getReleaseYear());
        movie.setDirector(newMovie.getDirector());
        Movie saved = movieRepo.save(movie);
        return mapToResponse(saved);
    }

    public MovieResponse findMovieById(Long id) {
        Movie movie = movieRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                "Movie with ID: %d, was not found", id
        )));
        return mapToResponse(movie);
    }

    public MovieResponse findMovieByIdWithRatings(Long id) {
        Movie movie = movieRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                "Movie with ID: %d, was not found", id
        )));
        return mapToResponseWithRatings(movie);
    }

    // Map Movie to MovieResponse without ratings
    private MovieResponse mapToResponse(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                movie.getDirector(),
                movie.getLanguage()
        );
    }

    // Map Movie to MovieResponse with ratings
    private MovieResponse mapToResponseWithRatings(Movie movie) {
        List<RatingResponse> ratings = movie.getRatings() != null
                ? movie.getRatings().stream()
                        .map(this::mapRatingToResponse)
                        .collect(Collectors.toList())
                : Collections.emptyList();
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                movie.getDirector(),
                movie.getLanguage(),
                ratings
        );
    }

    // Map Rating to RatingResponse
    private RatingResponse mapRatingToResponse(Rating rating) {
        Long movieId = rating.getMovie() != null ? rating.getMovie().getId() : null;
        String movieTitle = rating.getMovie() != null ? rating.getMovie().getTitle() : null;
        return new RatingResponse(rating.getId(), rating.getScore(), movieId, movieTitle);
    }
}
