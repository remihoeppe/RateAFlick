package com.example.demo.DTOs;

/**
 * Rating summary when embedded in a movie response. Omits movie id/title to avoid repetition.
 */
public record MovieRatingSummary(Long id, int score) {
}
