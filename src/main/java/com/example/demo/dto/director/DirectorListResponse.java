package com.example.demo.dto.director;

/**
 * Response for GET /api/v1/directors (list). Only id and name — no movies field.
 */
public class DirectorListResponse {
    private Long id;
    private String name;

    public DirectorListResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
