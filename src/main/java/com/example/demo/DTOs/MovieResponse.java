package com.example.demo.DTOs;

public class MovieResponse {
    private Long id;
    private String title;
    private int releaseYear;
    private String director;
    private String language;
    /** When present: items (array of ratings) + average score. */
    private MovieRatings ratings;

    // Constructor without ratings
    public MovieResponse(Long id, String title, int releaseYear, String director, String language) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.language = language;
        this.ratings = null;
    }

    // Constructor with ratings (items array + aggregate average)
    public MovieResponse(Long id, String title, int releaseYear, String director, String language, MovieRatings ratings) {
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

    public MovieRatings getRatings() {
        return ratings;
    }
}
