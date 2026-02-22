package com.example.demo.dto.director;

import java.util.List;

/**
 * Response for director endpoints. Movies are populated only for GET by ID.
 */
public class DirectorResponse {
    private Long id;
    private String name;
    private List<DirectorMovieSummary> movies;

    public DirectorResponse(Long id, String name) {
        this(id, name, List.of());
    }

    public DirectorResponse(Long id, String name, List<DirectorMovieSummary> movies) {
        this.id = id;
        this.name = name;
        this.movies = movies != null ? movies : List.of();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<DirectorMovieSummary> getMovies() {
        return movies;
    }
}
