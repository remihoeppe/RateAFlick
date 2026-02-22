package com.example.demo.dto.movie;

import java.util.List;

/**
 * Ratings for a movie: the list of individual ratings plus the aggregate average score.
 */
public record MovieRatings(
        List<MovieRatingSummary> items,
        double average
) {
    /**
     * Builds from a list of rating summaries, computing the average (0 if empty).
     */
    public static MovieRatings from(List<MovieRatingSummary> items) {
        if (items == null || items.isEmpty()) {
            return new MovieRatings(List.of(), 0.0);
        }
        double sum = 0;
        for (MovieRatingSummary r : items) {
            sum += r.score();
        }
        double avg = sum / items.size();
        return new MovieRatings(items, avg);
    }
}
