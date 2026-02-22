package com.example.demo.dto.user;

import com.example.demo.dto.rating.RatingResponse;

import java.util.List;

public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private List<RatingResponse> ratings;

    /** For list endpoint: id, name, email only; ratings are not loaded. */
    public UserResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.ratings = List.of();
    }

    public UserResponse(Long id, String name, String email, List<RatingResponse> ratings) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.ratings = ratings;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public List<RatingResponse> getRatings() {
        return ratings;
    }
}
