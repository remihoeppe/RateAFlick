package com.example.demo.dto.movie;

import jakarta.validation.constraints.*;

public class CreateMovieRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 char long")
    private String title;

    @NotNull(message = "Release Year is required")
    @Min(value = 1888, message = "Release Year must be at least 1888")
    @Max(value = 2100, message = "Release Year must be at most 2100")
    private Integer releaseYear;

    /** Optional ID of an existing Director. If null, the movie has no director. */
    private Long directorId;

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Long getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
    }
}
