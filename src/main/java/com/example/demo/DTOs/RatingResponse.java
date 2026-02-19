package com.example.demo.DTOs;

public class RatingResponse {
    private Long id;
    private int score;
    private Long movieId;
    private String movieTitle;

    public RatingResponse(Long id, int score, Long movieId, String movieTitle) {
        this.id = id;
        this.score = score;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
    }

    public Long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public Long getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
}
