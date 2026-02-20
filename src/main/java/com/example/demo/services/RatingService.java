package com.example.demo.services;

import com.example.demo.DTOs.CreateRatingRequest;
import com.example.demo.DTOs.RatingResponse;
import com.example.demo.models.Movie;
import com.example.demo.models.Rating;
import com.example.demo.models.User;
import com.example.demo.repositories.MovieRepository;
import com.example.demo.repositories.RatingRepository;
import com.example.demo.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {
    private final RatingRepository ratingRepo;
    private final UserRepository userRepo;
    private final MovieRepository movieRepo;

    public RatingService(RatingRepository ratingRepo, UserRepository userRepo, MovieRepository movieRepo) {
        this.ratingRepo = ratingRepo;
        this.userRepo = userRepo;
        this.movieRepo = movieRepo;
    }

    public RatingResponse createRating(CreateRatingRequest request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with ID: %d, was not found", request.getUserId())));

        Movie movie = movieRepo.findById(request.getMovieId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Movie with ID: %d, was not found", request.getMovieId())));

        Rating rating = new Rating();
        rating.setScore(request.getScore());
        rating.setUser(user);
        rating.setMovie(movie);

        Rating saved = ratingRepo.save(rating);
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

    public void deleteRating(Long id) {
        if (!ratingRepo.existsById(id)) {
            throw new EntityNotFoundException(
                    String.format("Rating with ID: %d, was not found", id));
        }
        ratingRepo.deleteById(id);
    }

    private RatingResponse mapToResponse(Rating rating) {
        Long movieId = rating.getMovie() != null ? rating.getMovie().getId() : null;
        String movieTitle = rating.getMovie() != null ? rating.getMovie().getTitle() : null;
        return new RatingResponse(rating.getId(), rating.getScore(), movieId, movieTitle);
    }
}
