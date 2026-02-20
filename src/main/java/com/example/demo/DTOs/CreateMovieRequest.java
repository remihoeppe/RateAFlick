package com.example.demo.DTOs;

import jakarta.validation.constraints.*;

public class CreateMovieRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 char long")
    private String title;

    @NotNull(message = "Release Year is required")
    @Min(value = 1888, message = "Release Year must be at least 1888")
    @Max(value = 2100, message = "Release Year must be at most 2100")
    private Integer releaseYear;

    @Size(max = 100, message = "Director name must be less than 100 characters")
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
