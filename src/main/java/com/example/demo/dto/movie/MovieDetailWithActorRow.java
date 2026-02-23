package com.example.demo.dto.movie;

/**
 * One row from the single-query movie detail: movie fields + director + avg + one actor (or null).
 * Multiple rows per movie when there are multiple actors; service deduplicates and builds the response.
 */
public interface MovieDetailWithActorRow {
    Long getId();
    String getTitle();
    int getReleaseYear();
    String getLanguage();
    String getDirectorName();
    Double getRatingsAvg();
    Long getActorId();
    String getActorName();
}
