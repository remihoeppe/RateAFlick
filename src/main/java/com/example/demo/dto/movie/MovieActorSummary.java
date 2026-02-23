package com.example.demo.dto.movie;

/**
 * Actor summary returned when fetching a movie by ID.
 */
public record MovieActorSummary(Long id, String name) {
}
