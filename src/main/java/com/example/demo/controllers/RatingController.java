package com.example.demo.controllers;

import com.example.demo.DTOs.CreateRatingRequest;
import com.example.demo.DTOs.PageResponse;
import com.example.demo.DTOs.RatingResponse;
import com.example.demo.services.RatingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ratings")
@Validated
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<RatingResponse>> getAllRatings(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(ratingService.findAllRatings(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingResponse> getRatingById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(ratingService.findRatingById(id));
    }

    @PostMapping
    public ResponseEntity<RatingResponse> createRating(@Valid @RequestBody CreateRatingRequest newRating) {
        RatingResponse createdRating = ratingService.createRating(newRating);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable @Min(1) Long id) {
        ratingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }
}
