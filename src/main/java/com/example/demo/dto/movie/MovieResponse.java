package com.example.demo.dto.movie;

public class MovieResponse {
    private Long id;
    private String title;
    private int releaseYear;
    private String director;
    private String language;
    /** Average rating score, or null if no ratings. */
    private Double ratingsAvg;

    public MovieResponse(Long id, String title, int releaseYear, String director, String language) {
        this(id, title, releaseYear, director, language, null);
    }

    public MovieResponse(Long id, String title, int releaseYear, String director, String language, Double ratingsAvg) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.director = director;
        this.language = language;
        this.ratingsAvg = ratingsAvg;
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
}
