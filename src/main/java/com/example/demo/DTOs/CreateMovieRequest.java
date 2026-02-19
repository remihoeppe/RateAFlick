package com.example.demo.DTOs;

import jakarta.validation.constraints.*;


public class CreateMovieRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message =
            "Name must be between 3 and 100 char long")
    private String title;

    @NotBlank(message = "Release Year is required")
    private int releaseYear;

    private String director;

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDirector() {
        return director;
    }
}
