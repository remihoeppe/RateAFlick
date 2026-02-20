package com.example.demo.services;

import com.example.demo.DTOs.CreateRatingRequest;
import com.example.demo.DTOs.RatingResponse;
import com.example.demo.exception.DuplicateRatingException;
import com.example.demo.models.Movie;
import com.example.demo.models.Rating;
import com.example.demo.models.User;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.repositories.RatingRepository;
import com.example.demo.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {
    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);
    
    private final RatingRepository ratingRepo;
    private final UserRepository userRepo;
    private final MovieRepository movieRepo;

    public RatingService(RatingRepository ratingRepo, UserRepository userRepo, MovieRepository movieRepo) {
        this.ratingRepo = ratingRepo;
        this.userRepo = userRepo;
        this.movieRepo = movieRepo;
    }

    @Transactional
    public RatingResponse createRating(CreateRatingRequest request) {
        logger.debug("Creating rating for user ID: {}, movie ID: {}, score: {}", 
                request.getUserId(), request.getMovieId(), request.getScore());
        
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> {
                    logger.warn("User with ID: {} not found", request.getUserId());
                    return new EntityNotFoundException(
                            String.format("User with ID: %d, was not found", request.getUserId()));
                });

        Movie movie = movieRepo.findById(request.getMovieId())
                .orElseThrow(() -> {
                    logger.warn("Movie with ID: {} not found", request.getMovieId());
                    return new EntityNotFoundException(
                            String.format("Movie with ID: %d, was not found", request.getMovieId()));
                });

        // Check for duplicate rating
        if (ratingRepo.findByUser_IdAndMovie_Id(request.getUserId(), request.getMovieId()).isPresent()) {
            logger.warn("Duplicate rating attempt: user ID: {}, movie ID: {}", 
                    request.getUserId(), request.getMovieId());
            throw new DuplicateRatingException(
                    String.format("User %d has already rated movie %d", 
                            request.getUserId(), request.getMovieId()));
        }

        Rating rating = new Rating();
        rating.setScore(request.getScore());
        rating.setUser(user);
        rating.setMovie(movie);

        Rating saved = ratingRepo.save(rating);
        logger.info("Created rating ID: {} for user ID: {}, movie ID: {}", 
                saved.getId(), request.getUserId(), request.getMovieId());
        return mapToResponse(saved);
    }

    public RatingResponse findRatingById(Long id) {
        Rating rating = ratingRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Rating with ID: %d, was not found", id)));
        return mapToResponse(rating);
    }

    public List<RatingResponse> findAllRatings() {
        return ratingRepo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Page<RatingResponse> findAllRatings(Pageable pageable) {
        logger.debug("Finding all ratings with pagination: page={}, size={}", 
                pageable.getPageNumber(), pageable.getPageSize());
        return ratingRepo.findAll(pageable).map(this::mapToResponse);
    }

    @Transactional
    public void deleteRating(Long id) {
        logger.debug("Deleting rating ID: {}", id);
        if (!ratingRepo.existsById(id)) {
            logger.warn("Rating with ID: {} not found", id);
            throw new EntityNotFoundException(
                    String.format("Rating with ID: %d, was not found", id));
        }
        ratingRepo.deleteById(id);
        logger.info("Deleted rating ID: {}", id);
    }

    private RatingResponse mapToResponse(Rating rating) {
        Long movieId = rating.getMovie() != null ? rating.getMovie().getId() : null;
        String movieTitle = rating.getMovie() != null ? rating.getMovie().getTitle() : null;
        return new RatingResponse(rating.getId(), rating.getScore(), movieId, movieTitle);
    }
}
