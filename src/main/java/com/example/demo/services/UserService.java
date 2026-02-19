package com.example.demo.services;

import com.example.demo.DTOs.CreateUserRequest;
import com.example.demo.DTOs.RatingResponse;
import com.example.demo.DTOs.UpdateUserRequest;
import com.example.demo.DTOs.UserResponse;
import com.example.demo.models.Rating;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // CREATE ON POST Request
    public UserResponse addUser(CreateUserRequest newUserRequest) {
        if (userRepo.existsByEmail(newUserRequest.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + newUserRequest.getEmail());
        }
        User user = new User();
        user.setName(newUserRequest.getName());
        user.setEmail(newUserRequest.getEmail());
        User saved = userRepo.save(user);
        return mapToResponse(saved);
    }

    // READ on GET request - returns DTO without ratings
    public UserResponse findUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "User with ID: %d, was not found", id)));
        return mapToResponse(user);
    }

    // READ with ratings - returns DTO with ratings
    public UserResponse findUserByIdWithRatings(Long id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "User with ID: %d, was not found", id)));
        return mapToResponseWithRatings(user);
    }

    public List<UserResponse> findAllUsers() {
        return userRepo.findAll().stream().map(this::mapToResponse).toList();
    }

    // UPDATE
    public UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(
                        "User with ID: %d, was not found", id)));
        user.setName(updateUserRequest.getName());
        user.setEmail(updateUserRequest.getEmail());
        User updated = userRepo.save(user);
        return mapToResponse(updated);
    }

    // DELETE
    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new EntityNotFoundException(String.format(
                    "User with ID: %d, was not found", id));
        }
        userRepo.deleteById(id);
    }

    // Map User to UserResponse without ratings
    private UserResponse mapToResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    // Map User to UserResponse with ratings
    private UserResponse mapToResponseWithRatings(User user) {
        List<RatingResponse> ratings = user.getRatings() != null
                ? user.getRatings().stream()
                        .map(this::mapRatingToResponse)
                        .collect(Collectors.toList())
                : Collections.emptyList();
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), ratings);
    }

    // Map Rating to RatingResponse
    private RatingResponse mapRatingToResponse(Rating rating) {
        Long movieId = rating.getMovie() != null ? rating.getMovie().getId() : null;
        String movieTitle = rating.getMovie() != null ? rating.getMovie().getTitle() : null;
        return new RatingResponse(rating.getId(), rating.getScore(), movieId, movieTitle);
    }
}
