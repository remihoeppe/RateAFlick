package com.example.demo.services;

import com.example.demo.DTOs.CreateUserRequest;
import com.example.demo.DTOs.RatingResponse;
import com.example.demo.DTOs.UpdateUserRequest;
import com.example.demo.DTOs.UserResponse;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.models.Rating;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // CREATE ON POST Request
    @Transactional
    public UserResponse addUser(CreateUserRequest newUserRequest) {
        logger.debug("Creating user with email: {}", newUserRequest.getEmail());
        if (userRepo.existsByEmail(newUserRequest.getEmail())) {
            logger.warn("Email already in use: {}", newUserRequest.getEmail());
            throw new EmailAlreadyExistsException("Email already in use: " + newUserRequest.getEmail());
        }
        User user = new User();
        user.setName(newUserRequest.getName());
        user.setEmail(newUserRequest.getEmail());
        User saved = userRepo.save(user);
        logger.info("Created user ID: {} with email: {}", saved.getId(), saved.getEmail());
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

    public Page<UserResponse> findAllUsers(Pageable pageable) {
        logger.debug("Finding all users with pagination: page={}, size={}", 
                pageable.getPageNumber(), pageable.getPageSize());
        return userRepo.findAll(pageable).map(this::mapToResponse);
    }

    // UPDATE - Partial update: only updates fields that are provided (non-null and
    // non-blank)
    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {
        logger.debug("Updating user ID: {}", id);
        User user = userRepo.findById(id).orElseThrow(
                () -> {
                    logger.warn("User with ID: {} not found", id);
                    return new EntityNotFoundException(String.format(
                            "User with ID: %d, was not found", id));
                });

        // Only update name if provided (non-null and non-blank)
        if (updateUserRequest.getName() != null) {
            String trimmedName = updateUserRequest.getName().trim();
            if (trimmedName.isEmpty()) {
                logger.warn("Empty name provided for user ID: {}", id);
                throw new IllegalArgumentException("Name cannot be empty or whitespace-only");
            }
            user.setName(trimmedName);
            logger.debug("Updated name for user ID: {}", id);
        }

        // Only update email if provided (non-null and non-blank)
        if (updateUserRequest.getEmail() != null) {
            String trimmedEmail = updateUserRequest.getEmail().trim();
            if (trimmedEmail.isEmpty()) {
                logger.warn("Empty email provided for user ID: {}", id);
                throw new IllegalArgumentException("Email cannot be empty or whitespace-only");
            }
            // Check if email is already in use by another user
            if (userRepo.existsByEmail(trimmedEmail) &&
                    !user.getEmail().equals(trimmedEmail)) {
                logger.warn("Email already in use: {} for user ID: {}", trimmedEmail, id);
                throw new EmailAlreadyExistsException("Email already in use: " + trimmedEmail);
            }
            user.setEmail(trimmedEmail);
            logger.debug("Updated email for user ID: {}", id);
        }

        User updated = userRepo.save(user);
        logger.info("Updated user ID: {}", id);
        return mapToResponse(updated);
    }

    // DELETE
    @Transactional
    public void deleteUser(Long id) {
        logger.debug("Deleting user ID: {}", id);
        if (!userRepo.existsById(id)) {
            logger.warn("User with ID: {} not found", id);
            throw new EntityNotFoundException(String.format(
                    "User with ID: %d, was not found", id));
        }
        userRepo.deleteById(id);
        logger.info("Deleted user ID: {}", id);
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
