package com.example.demo.DTOs;

import java.util.List;

public class MovieResponse {
    private Long id;
    private String title;
    private int releaseYear;
    private String director;
    private String language;
    private List<RatingResponse> ratings;

    // Constructor without ratings
    public MovieResponse(Long id, String title, int releaseYear, String director, String language) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.language = language;
        this.ratings = null;
    }

    // Constructor with ratings
    public MovieResponse(Long id, String title, int releaseYear, String director, String language, List<RatingResponse> ratings) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.language = language;
        this.ratings = ratings;
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

    public List<RatingResponse> getRatings() {
        return ratings;
    }
}
