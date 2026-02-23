package com.example.demo.dto.movie;

import java.util.List;

public class MovieResponse {
    private Long id;
    private String title;
    private int releaseYear;
    private String director;
    private String language;
    /** Average rating score, or null if no ratings. */
    private Double ratingsAvg;
    /** Actors in this movie; null or empty when not loaded (e.g. list endpoint). */
    private List<MovieActorSummary> actors;

    public MovieResponse(Long id, String title, int releaseYear, String director, String language) {
        this(id, title, releaseYear, director, language, null, null);
    }

    public MovieResponse(Long id, String title, int releaseYear, String director, String language, Double ratingsAvg) {
        this(id, title, releaseYear, director, language, ratingsAvg, null);
    }

    public MovieResponse(Long id, String title, int releaseYear, String director, String language,
                         Double ratingsAvg, List<MovieActorSummary> actors) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.language = language;
        this.ratingsAvg = ratingsAvg;
        this.actors = actors;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public String getLanguage() {
        return language;
    }

    public Double getRatingsAvg() {
        return ratingsAvg;
    }

    public List<MovieActorSummary> getActors() {
        return actors;
    }
}
