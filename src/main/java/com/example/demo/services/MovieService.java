package com.example.demo.services;

import com.example.demo.DTOs.CreateMovieRequest;
import com.example.demo.DTOs.MovieRatingSummary;
import com.example.demo.DTOs.MovieRatings;
import com.example.demo.DTOs.MovieResponse;
import com.example.demo.DTOs.PageResponse;
import com.example.demo.models.Movie;
import com.example.demo.models.Rating;
import com.example.demo.repositories.DirectorRepository;
import com.example.demo.repositories.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepo;
    private final DirectorRepository directorRepo;

    public MovieService(MovieRepository movieRepo, DirectorRepository directorRepo) {
        this.movieRepo = movieRepo;
        this.directorRepo = directorRepo;
    }

    @Transactional
    public MovieResponse createMovie(CreateMovieRequest newMovie) {
        logger.debug("Creating movie with title: {}", newMovie.getTitle());
        Movie movie = new Movie();
        movie.setTitle(newMovie.getTitle());
        movie.setReleaseYear(newMovie.getReleaseYear());
        if (newMovie.getDirectorId() != null) {
            movie.setDirector(directorRepo.findById(newMovie.getDirectorId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Director with ID: " + newMovie.getDirectorId() + " not found")));
        }
        Movie saved = movieRepo.save(movie);
        logger.info("Created movie ID: {} with title: {}", saved.getId(), saved.getTitle());
        return mapToResponse(saved);
    }

    public MovieResponse findMovieById(Long id) {
        Movie movie = movieRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "Movie with ID: %d, was not found", id)));
        return mapToResponse(movie);
    }

    public MovieResponse findMovieByIdWithRatings(Long id) {
        Movie movie = movieRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "Movie with ID: %d, was not found", id)));
        return mapToResponseWithRatings(movie);
    }

    public PageResponse<MovieResponse> findAllMovies(Pageable pageable) {
        logger.debug("Finding all movies with pagination: page={}, size={}",
                pageable.getPageNumber(), pageable.getPageSize());
        Page<Movie> page = movieRepo.findAll(pageable);
        List<Long> ids = page.getContent().stream().map(Movie::getId).toList();
        if (ids.isEmpty()) {
            return PageResponse.of(page.map(this::mapToResponseWithRatings));
        }
        List<Movie> withRatings = movieRepo.findAllByIdInWithRatingsAndDirector(ids);
        Map<Long, Movie> byId = withRatings.stream().collect(Collectors.toMap(Movie::getId, m -> m));
        List<MovieResponse> content = ids.stream()
                .map(byId::get)
                .map(this::mapToResponseWithRatings)
                .toList();
        return new PageResponse<>(
                content,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isFirst(),
                page.isLast(),
                content.size());
    }

    // Map Movie to MovieResponse without ratings
    private MovieResponse mapToResponse(Movie movie) {
        String directorName = movie.getDirector() != null ? movie.getDirector().getName() : null;
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                directorName,
                movie.getLanguage());
    }

    // Map Movie to MovieResponse with ratings (items + aggregate average)
    private MovieResponse mapToResponseWithRatings(Movie movie) {
        List<MovieRatingSummary> items = movie.getRatings() != null
                ? movie.getRatings().stream()
                        .map(this::toMovieRatingSummary)
                        .collect(Collectors.toList())
                : Collections.emptyList();
        MovieRatings ratings = MovieRatings.from(items);
        String directorName = movie.getDirector() != null ? movie.getDirector().getName() : null;
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                directorName,
                movie.getLanguage(),
                ratings);
    }

    private MovieRatingSummary toMovieRatingSummary(Rating rating) {
        return new MovieRatingSummary(rating.getId(), rating.getScore());
    }
}
