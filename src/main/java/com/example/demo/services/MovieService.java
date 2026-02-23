package com.example.demo.services;

import com.example.demo.dto.common.PageResponse;
import com.example.demo.dto.movie.CreateMovieRequest;
import com.example.demo.dto.movie.MovieActorSummary;
import com.example.demo.dto.movie.MovieResponse;
import com.example.demo.models.Movie;
import com.example.demo.repositories.DirectorRepository;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.repositories.RatingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepo;
    private final DirectorRepository directorRepo;
    private final RatingRepository ratingRepo;

    public MovieService(MovieRepository movieRepo, DirectorRepository directorRepo, RatingRepository ratingRepo) {
        this.movieRepo = movieRepo;
        this.directorRepo = directorRepo;
        this.ratingRepo = ratingRepo;
    }

    // CREATE on POST Request
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

    // READ on GET Request - returns DTO with artists (director + actors loaded via JOIN FETCH)
    @Transactional(readOnly = true)
    public MovieResponse find(Long id) {
        logger.debug("Finding movie with ID: {}", id);
        Movie movie = movieRepo.findByIdWithDirectorAndActors(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "Movie with ID: %d, was not found", id)));
        logger.debug("Movie.director: {}", movie.getDirector());
        return mapToResponseWithArtists(movie);
    }

    // READ on GET Request - returns DTO with average rating for a movie
    public MovieResponse findWithAverageRating(Long id) {
        Movie movie = movieRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "Movie with ID: %d, was not found", id)));
        double average = getAverageRating(movie);
        return mapToResponseWithRatingAverage(movie, average);
    }

    // READ on GET Request - returns DTO with all movies
    public PageResponse<MovieResponse> findAllMovies(Pageable pageable) {
        logger.debug("Finding all movies with pagination: page={}, size={}",
                pageable.getPageNumber(), pageable.getPageSize());
        return PageResponse.of(movieRepo.findMovieListPage(pageable).map(this::rowToMovieResponse));
    }

    /**
     * Map native query row [id, title, release_year, language, director_name,
     * ratings_avg] to MovieResponse.
     */
    private MovieResponse rowToMovieResponse(Object[] row) {
        Long id = ((Number) row[0]).longValue();
        String title = (String) row[1];
        int releaseYear = ((Number) row[2]).intValue();
        String language = (String) row[3];
        String directorName = row[4] != null ? (String) row[4] : null;
        double ratingsAvg = row[5] != null ? ((Number) row[5]).doubleValue() : 0.0;
        return new MovieResponse(id, title, releaseYear, directorName, language, ratingsAvg);
    }

    // Map Movie to MovieResponse without ratings (no actors)
    private MovieResponse mapToResponse(Movie movie) {
        String directorName = movie.getDirector() != null ? movie.getDirector().getName() : null;
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                directorName,
                movie.getLanguage());
    }

    // Map Movie to MovieResponse with actors (for GET by id)
    private MovieResponse mapToResponseWithArtists(Movie movie) {
        logger.debug("Mapping movie with ID: {} to MovieResponse with artists", movie.getId());
        logger.debug("Movie.director: {}", movie.getDirector());
        String directorName = movie.getDirector() != null ? movie.getDirector().getName() : null;
        double average = getAverageRating(movie);
        List<MovieActorSummary> actors = movie.getActors() == null
                ? Collections.emptyList()
                : movie.getActors().stream()
                        .map(a -> new MovieActorSummary(a.getId(), a.getName()))
                        .collect(Collectors.toList());
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                directorName,
                movie.getLanguage(),
                average,
                actors);
    }

    private MovieResponse mapToResponseWithRatingAverage(Movie movie, double average) {
        String directorName = movie.getDirector() != null ? movie.getDirector().getName() : null;
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                directorName,
                movie.getLanguage(),
                average);
    }

    private double getAverageRating(Movie movie) {
        List<Object[]> avgRows = ratingRepo.findAverageScoreByMovieIdIn(List.of(movie.getId()));
        return avgRows.isEmpty() ? 0.0 : ((Number) avgRows.get(0)[1]).doubleValue();
    }

}
