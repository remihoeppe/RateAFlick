package com.example.demo.services;

import com.example.demo.DTOs.CreateMovieRequest;
import com.example.demo.DTOs.MovieResponse;
import com.example.demo.DTOs.PageResponse;
import com.example.demo.models.Movie;
import com.example.demo.repositories.DirectorRepository;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.repositories.RatingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        List<Movie> withDirector = movieRepo.findAllByIdInWithDirector(List.of(id));
        if (withDirector.isEmpty()) {
            throw new EntityNotFoundException(String.format("Movie with ID: %d, was not found", id));
        }
        Movie movie = withDirector.get(0);
        List<Object[]> avgRows = ratingRepo.findAverageScoreByMovieIdIn(List.of(id));
        double avg = avgRows.isEmpty() ? 0.0 : ((Number) avgRows.get(0)[1]).doubleValue();
        return mapToResponseWithRatingAverage(movie, avg);
    }

    public PageResponse<MovieResponse> findAllMovies(Pageable pageable) {
        logger.debug("Finding all movies with pagination: page={}, size={}",
                pageable.getPageNumber(), pageable.getPageSize());
        Page<Object[]> page = movieRepo.findMovieListPage(pageable);
        List<MovieResponse> content = page.getContent().stream()
                .map(this::rowToMovieResponse)
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

    /** Map native query row [id, title, release_year, language, director_name, ratings_avg] to MovieResponse. */
    private MovieResponse rowToMovieResponse(Object[] row) {
        Long id = ((Number) row[0]).longValue();
        String title = (String) row[1];
        int releaseYear = ((Number) row[2]).intValue();
        String language = (String) row[3];
        String directorName = row[4] != null ? (String) row[4] : null;
        double ratingsAvg = row[5] != null ? ((Number) row[5]).doubleValue() : 0.0;
        return new MovieResponse(id, title, releaseYear, directorName, language, ratingsAvg);
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
}
