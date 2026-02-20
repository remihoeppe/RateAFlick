package com.example.demo.controllers;

import com.example.demo.DTOs.CreateRatingRequest;
import com.example.demo.DTOs.RatingResponse;
import com.example.demo.services.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<List<RatingResponse>> getAllRatings() {
        return ResponseEntity.ok(ratingService.findAllRatings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable Long id) {
        return ResponseEntity.ok(ratingService.findRatingById(id));
    }

    @PostMapping
    public ResponseEntity<RatingResponse> createRating(@Validated @RequestBody CreateRatingRequest newRating) {
        RatingResponse createdRating = ratingService.createRating(newRating);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }
}
